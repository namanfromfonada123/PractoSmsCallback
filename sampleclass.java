package BOOT-INF.classes.com.rcs.fonada.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rcs.fonada.blackList.request.BlackListRequestAPI;
import com.rcs.fonada.common.DataContainer;
import com.rcs.fonada.entity.BlackListEntity;
import com.rcs.fonada.entity.DemoRcsModel;
import com.rcs.fonada.entity.TokenPojo;
import com.rcs.fonada.pdf.model.PDFRequest;
import com.rcs.fonada.pdf.model.PdfResponse;
import com.rcs.fonada.repository.BlackListRepository;
import com.rcs.fonada.service.BotTokenAPIService;
import com.rcs.fonada.service.RabbitMQSender;
import com.rcs.fonada.vi.bot.request.BotApiRequest;
import com.rcs.fonada.vi.bot.request.MessageContact;
import com.rcs.fonada.vi.bot.request.RCSMessage;
import com.rcs.fonada.vi.bot.request.TemplateMessage;
import com.rcs.fonada.vi.bot.response.BotApiResponse;
import com.rcs.fonada.vi.bot.response.CustomParams;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BlackListService {
    Logger log = LoggerFactory.getLogger(com.rcs.fonada.service.BlackListService.class);

    @Value("${rcsFonada.leadApi}")
    public String rcsFonadaLeadApi;

    @Value("${bot.fileUrl}")
    public String botfileUrl;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Value("${templateCode}")
    public String templateCode;

    @Autowired
    private BotTokenAPIService botTokenService;

    @Autowired
    private BlackListRepository blackListRepository;

    public DataContainer saveBlackList(String blackListNo) {
        this.log.info("******** Inside BlackListService.saveBlackList() *****");
        DataContainer data = new DataContainer();
        BlackListEntity blacklist = new BlackListEntity();
        if (blackListNo != null) {
            if (this.blackListRepository.findByBlacklistMsisdn(blackListNo) == null) {
                blacklist.setBlacklistMsisdn(blackListNo);
                blacklist.setCreatedDate(convertDateToString(new Date()));
                this.blackListRepository.save(blacklist);
                data.setMsg("Success");
                data.setStatus(Integer.valueOf(200));
                this.log.info("********  BlackListService.saveBlackList() Response:: " + data.toString());
            } else {
                data.setMsg("Msisdn Already Blacklisted.");
                data.setStatus(Integer.valueOf(405));
                this.log.info("********  BlackListService.saveBlackList() Response:: " + data.toString());
            }
        } else {
            data.setMsg("Msisdn Cannot Be Empty.");
            data.setStatus(Integer.valueOf(400));
            this.log.info("********  BlackListService.saveBlackList() Response:: " + data.toString());
        }
        return data;
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public DataContainer findBlackListNo(String blackListNo) {
        this.log.info("********  Inside BlackListService.findBlackListNo() ********");
        DataContainer data = null;
        TokenPojo pojo = null;
        String responseFromViBotAPI = "";
        BotApiResponse botApiResponse = null;
        BlackListEntity blacklist = null;
        try {
            data = new DataContainer();
            blacklist = this.blackListRepository.findByBlacklistMsisdn(blackListNo);
            if (blacklist == null) {
                pojo = (TokenPojo)(new Gson()).fromJson(this.botTokenService.getTokenFromClientAPI(), TokenPojo.class);
                this.log.info("********  Response From BlackListService.getTokenFromClientAPI():: " + pojo.toString());
                if (pojo.getAccess_token() != null || pojo.getAccess_token().equals("") ||
                        !pojo.getAccess_token().isEmpty()) {
                    BotApiRequest botRequest = new BotApiRequest();
                    TemplateMessage templateMessage = new TemplateMessage();
                    MessageContact messageContact = new MessageContact();
                    RCSMessage RCSMessage = new RCSMessage();
                    messageContact.setUserContact(blackListNo);
                    CustomParams customParam = new CustomParams();
                    customParam.setAmount("5000");
                    customParam.setDate(new Date());
                    templateMessage.setTemplateCode(this.templateCode);
                    RCSMessage.setTemplateMessage(templateMessage);
                    botRequest.setRCSMessage(RCSMessage);
                    botRequest.setMessageContact(messageContact);
                    this.log.info("********  Request For botTokenService.sendMessageByMSISDToClientAPI():: " + (new Gson())
                            .toJson(botRequest).toString());
                    responseFromViBotAPI = this.botTokenService.sendMessageByMSISDToClientAPI((new Gson())
                            .toJson(botRequest).toString(), pojo.getAccess_token());
                    this.log.info("******** Response " + responseFromViBotAPI);
                    botApiResponse = (BotApiResponse)(new Gson()).fromJson(responseFromViBotAPI, BotApiResponse.class);
                    this.log.info("******** Response From botTokenService.sendMessageByMSISDToClientAPI():: " + botApiResponse
                            .toString());
                    data.setData(botApiResponse);
                    if (botApiResponse.getRCSMessage().getStatus().equalsIgnoreCase("failed")) {
                        data.setData(botApiResponse);
                        data.setMsg("Failed");
                        data.setStatus(Integer.valueOf(400));
                    } else {
                        data.setData(botApiResponse);
                        data.setMsg("Success");
                        data.setStatus(Integer.valueOf(200));
                    }
                } else {
                    data.setMsg("Couldn't Received Token From VI.");
                    data.setStatus(Integer.valueOf(400));
                    this.log.info("******** Response From botTokenService.sendMessageByMSISDToClientAPI():: " + data
                            .toString());
                }
            } else {
                data.setMsg("Message Could Not Be Send Because Of BlackList Number.");
                data.setStatus(Integer.valueOf(400));
                this.log.info("******** Response From botTokenService.sendMessageByMSISDToClientAPI():: " + data.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.setMsg("Got Exception::" + e.getLocalizedMessage());
            data.setStatus(Integer.valueOf(400));
            this.log.info("******** Response From botTokenService.sendMessageByMSISDToClientAPI():: " + data.toString());
            return data;
        }
        return data;
    }

    public DataContainer findBlackListByBlackListRequest(String blackListRequest) {
        this.log.info("********  Inside BlackListService.findBlackListByBlackListRequest() ********");
        ObjectMapper objectMapper = new ObjectMapper();
        BlackListRequestAPI blackListRequestAPI = null;
        DataContainer data = null;
        BlackListEntity blacklistapi = null;
        try {
            blackListRequestAPI = (BlackListRequestAPI)objectMapper.readValue(blackListRequest, BlackListRequestAPI.class);
            data = new DataContainer();
            String input = blackListRequestAPI.getMessageContact().getUserContact();
            String mobileNo = "";
            if (input.length() == 13) {
                this.log.info("input.length() == 13:: " + input.length());
                mobileNo = input.substring(input.length() - 10);
            } else if (input.length() == 12) {
                this.log.info("input.length() == 12:: " + input.length());
                mobileNo = input.substring(input.length() - 10);
            } else if (input.length() == 11) {
                this.log.info("input.length() == 11:: " + input.length());
                mobileNo = input.substring(input.length() - 10);
            } else {
                this.log.info("input.length():: " + input.length());
                mobileNo = input;
            }
            blacklistapi = new BlackListEntity();
            if (blackListRequestAPI.getRCSMessage() != null &&
                    blackListRequestAPI.getRCSMessage().getSuggestedResponse() != null)
                if (blackListRequestAPI.getRCSMessage().getSuggestedResponse().getResponse() != null)
                    if (blackListRequestAPI.getRCSMessage().getSuggestedResponse().getResponse().getAction() != null) {
                        if (blackListRequestAPI.getRCSMessage().getSuggestedResponse().getResponse().getAction()
                                .getDisplayText() != null)
                            blacklistapi.setDisplayText(blackListRequestAPI.getRCSMessage().getSuggestedResponse()
                                    .getResponse().getAction().getDisplayText());
                        if (blackListRequestAPI.getRCSMessage().getSuggestedResponse().getResponse().getAction()
                                .getPostback() != null &&
                                blackListRequestAPI.getRCSMessage().getSuggestedResponse().getResponse().getAction()
                                        .getPostback().getData() != null)
                            blacklistapi.setData(blackListRequestAPI.getRCSMessage().getSuggestedResponse()
                                    .getResponse().getAction().getPostback().getData());
                        blacklistapi.setStatus("click");
                    } else if (blackListRequestAPI.getRCSMessage().getSuggestedResponse().getResponse().getReply()
                            .getPostback() != null) {
                        if (blackListRequestAPI.getRCSMessage().getSuggestedResponse().getResponse().getReply()
                                .getPostback().getData() != null)
                            blacklistapi.setData(blackListRequestAPI.getRCSMessage().getSuggestedResponse()
                                    .getResponse().getReply().getPostback().getData());
                        if (blackListRequestAPI.getRCSMessage().getSuggestedResponse().getResponse().getReply()
                                .getDisplayText() != null)
                            blacklistapi.setDisplayText(blackListRequestAPI.getRCSMessage().getSuggestedResponse()
                                    .getResponse().getReply().getDisplayText());
                    }
            blacklistapi.setSendDlr(Integer.valueOf(0));
            blacklistapi.setIsComplete("0");
            blacklistapi.setEvent(blackListRequestAPI.getEvent());
            blacklistapi.setMsgId(blackListRequestAPI.getRCSMessage().getMsgId());
            blacklistapi.setTextMessage(blackListRequestAPI.getRCSMessage().getTextMessage());
            blacklistapi.setTimestamp(blackListRequestAPI.getRCSMessage().getTimestamp());
            blacklistapi.setCreatedDate(convertDateToString(new Date()));
            if (Objects.nonNull(blackListRequestAPI.getMessageContact()))
                blacklistapi.setBlacklistMsisdn(mobileNo);
            if (blackListRequestAPI.getRCSMessage().getStatus() != null)
                blacklistapi.setStatus(blackListRequestAPI.getRCSMessage().getStatus());
            this.rabbitMQSender.send(blacklistapi);
            data.setMsg("Added Successfully");
            data.setStatus(Integer.valueOf(200));
            this.log.info("******** Response From botTokenService.sendMessageByMSISDToClientAPI():: " + data.toString());
        } catch (Exception e) {
            e.printStackTrace();
            data.setMsg("Got Exception::" + e.getLocalizedMessage());
            data.setStatus(Integer.valueOf(400));
            this.log.info("******** Response From botTokenService.sendMessageByMSISDToClientAPI():: " + data.toString());
            return data;
        }
        return data;
    }

    public DataContainer pdfRcsCallBackRequest(String blackListRequest) {
        this.log.info("********  Inside PDF CALL BACK BlackListService.pdfRcsCallBackRequest() ********");
        ObjectMapper objectMapper = new ObjectMapper();
        PDFRequest blackListRequestAPI = null;
        DataContainer data = null;
        BlackListEntity blacklistapi = null;
        PdfResponse decodeData = null;
        try {
            blacklistapi = new BlackListEntity();
            blackListRequestAPI = (PDFRequest)objectMapper.readValue(blackListRequest, PDFRequest.class);
            byte[] encodedString = Base64.getDecoder().decode(blackListRequestAPI.getMessage().getData());
            String decodedStr = new String(encodedString, StandardCharsets.UTF_8);
            this.log.info("******** DECODE STRING :_: " + decodedStr);
            decodeData = (PdfResponse)objectMapper.readValue(decodedStr, PdfResponse.class);
            this.log.info("******** DECODE STRING JSON TO JAVA MODEL :_: " + decodeData.toString());
            data = new DataContainer();
            String input = decodeData.getSenderPhoneNumber();
            String mobileNo = "";
            if (input.length() == 13) {
                this.log.info("input.length() == 13:: " + input.length());
                mobileNo = input.substring(input.length() - 10);
            } else if (input.length() == 12) {
                this.log.info("input.length() == 12:: " + input.length());
                mobileNo = input.substring(input.length() - 10);
            } else if (input.length() == 11) {
                this.log.info("input.length() == 11:: " + input.length());
                mobileNo = input.substring(input.length() - 10);
            } else {
                this.log.info("input.length():: " + input.length());
                mobileNo = input;
            }
            blacklistapi.setSendDlr(Integer.valueOf(0));
            blacklistapi.setIsComplete("0");
            if (decodedStr.contains("eventType")) {
                blacklistapi.setEvent((decodeData.getEventType() == null) ? "GOT NULL" : decodeData.getEventType());
            } else if (Objects.nonNull(decodeData.getSuggestionResponse())) {
                blacklistapi.setEvent((decodeData.getSuggestionResponse().getType() == null) ? "GOT NULL" : decodeData.getSuggestionResponse().getType());
            } else {
                blacklistapi.setEvent("GOT NULL");
            }
            blacklistapi.setMsgId(decodeData.getMessageId());
            if (decodedStr.contains("text"))
                blacklistapi.setTextMessage((decodeData.getText() != null) ? decodeData.getText() : (
                        (decodeData.getSuggestionResponse().getText() == null) ? "TEXT" : decodeData.getSuggestionResponse().getText()));
            blacklistapi.setTimestamp(decodeData.getSendTime());
            blacklistapi.setCreatedDate(convertDateToString(new Date()));
            if (Objects.nonNull(decodeData.getSenderPhoneNumber()))
                blacklistapi.setBlacklistMsisdn(mobileNo);
            blacklistapi.setStatus(decodeData.getReason());
            blacklistapi.setBotId(blackListRequestAPI.getMessage().getAttributes().getBusiness_id());
            this.rabbitMQSender.send(blacklistapi);
            this.log.info("******** PUSHED PDF CALLBACK TO EXCHANGE.CALLBACK QUEUE :_: " + decodeData.toString());
            data.setMsg("Added Successfully");
            data.setStatus(Integer.valueOf(200));
            this.log.info("******** Response From botTokenService.pdfRcsCallBackRequest():: " + data.toString());
        } catch (Exception e) {
            e.printStackTrace();
            data.setMsg("Got Exception::" + e.getLocalizedMessage());
            data.setStatus(Integer.valueOf(400));
            this.log.info("******** Response From botTokenService.pdfRcsCallBackRequest():: " + data.toString());
            return data;
        }
        return data;
    }

    public void findListBasedTextMessageIsNotNull() {
        this.log.info("******** findListBasedTextMessageIsNotNull Schedular Started **********");
        List<BlackListEntity> listBlackList = null;
        listBlackList = this.blackListRepository.findByTextMessageNotNullAndIsCompleteNotOne();
        try {
            if (listBlackList.size() > 0) {
                this.log.info("******** Got size from  findByTextMessageNotNullAndIsCompleteNotOne() size::" + listBlackList
                        .size());
                for (BlackListEntity blackList : listBlackList) {
                    RestTemplate restTemplate = new RestTemplate();
                    DemoRcsModel demoRcs = null;
                    Thread.sleep(1000L);
                    String uri = this.rcsFonadaLeadApi + blackList.getBlacklistMsisdn().trim();
                    System.out.println(uri);
                    ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class, new Object[0]);
                    this.log.info("response.getBody() ::" + (String)response.getBody());
                    demoRcs = (DemoRcsModel)(new Gson()).fromJson((String)response.getBody(), DemoRcsModel.class);
                    if (Objects.nonNull(demoRcs)) {
                        this.log.info("Going On Update blackList fo response.getBody() ::");
                        blackList.setCampaignName(demoRcs.getCampaignName());
                        blackList.setCampaignType(demoRcs.getCampaignType());
                        blackList.setUserName(demoRcs.getUserName());
                        blackList.setDataSourceName(demoRcs.getDataSourceName());
                        blackList.setBotId(demoRcs.getBotId());
                        blackList.setLeadName(demoRcs.getLeadName());
                        blackList.setIsComplete("1");
                        blackList.setTemplateCode(demoRcs.getTemplateCode());
                    } else {
                        blackList.setIsComplete("2");
                    }
                    this.blackListRepository.save(blackList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.log.info("******** findListBasedTextMessageIsNotNull Schedular Ended **********");
    }
}


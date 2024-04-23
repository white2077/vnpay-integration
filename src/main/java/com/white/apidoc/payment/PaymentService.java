package com.white.apidoc.payment;

import com.white.apidoc.core.config.payment.VNPAYConfig;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class PaymentService {
    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        String locate = request.getParameter("language");

        String vnpTxnRefund = VNPAYConfig.getRandomNumber(8);
        String vnpIpAddress = VNPAYConfig.  getIpAddress(request);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

        //put param begin
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", VNPAYConfig.vnp_Version);
        vnpParamsMap.put("vnp_Command", VNPAYConfig.vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", VNPAYConfig.vnp_TmnCode);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_TxnRef", vnpTxnRefund);
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" + vnpTxnRefund);
        vnpParamsMap.put("vnp_OrderType", VNPAYConfig.orderType);

        if (locate != null && !locate.isEmpty()) {
            vnpParamsMap.put("vnp_Locale", locate);
        } else {
            vnpParamsMap.put("vnp_Locale", "vn");
        }
        vnpParamsMap.put("vnp_ReturnUrl", VNPAYConfig.vnp_ReturnUrl);
        vnpParamsMap.put("vnp_IpAddr", vnpIpAddress);

        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        //put param end

        List<String> listOfKey = new ArrayList<>(vnpParamsMap.keySet());
        Collections.sort(listOfKey);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> iteratorKey = listOfKey.iterator();
        while (iteratorKey.hasNext()) {
            String keyName = iteratorKey.next();
            String valueName = vnpParamsMap.get(keyName);
            if ((valueName != null) && (!valueName.isEmpty())) {
                //Build hash data
                hashData.append(keyName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(valueName, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(keyName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(valueName, StandardCharsets.US_ASCII));
                if (iteratorKey.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnpSecureHash = VNPAYConfig.hmacSHA512(VNPAYConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = VNPAYConfig.vnp_PayUrl + "?" + queryUrl;
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }
}

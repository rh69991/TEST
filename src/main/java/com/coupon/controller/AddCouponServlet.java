package com.coupon.controller;

import core.vo.Core;
import coupon.service.CouponService;
import coupon.service.impl.CouponServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "addCouponServlet", value = "/backend/coupon/addCoupon.do")
public class AddCouponServlet extends HttpServlet {

    private CouponService service;

    @Override
    public void init() throws ServletException {
        service = new CouponServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        res.setCharacterEncoding("UTF-8");
        /* 允許跨域主機地址 */
        res.setHeader("Access-Control-Allow-Origin", "*");
        /* 允許跨域 GET, POST, HEAD 等 */
        res.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新預檢驗跨域的緩存時間 (s) */
        res.setHeader("Access-Control-Max-Age", "3600");
        /* 允許跨域的請求頭 */
        res.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否攜帶 cookie */
        res.setHeader("Access-Control-Allow-Credentials", "true");

        final String couponInfo = req.getParameter("couponInfo");
        final Integer couponValue = Integer.valueOf(req.getParameter("couponValue"));
        final Integer minimum = Integer.valueOf(req.getParameter("minimum"));
        final Core core = new Core();

        if (couponInfo == null) {
            core.setMessage("折價券說明空白");
            core.setSuccessful(false);
        } else if (couponValue <= 0) {
            core.setMessage("折價券面額不得小於等於0");
            core.setSuccessful(false);
        } else if (minimum <= couponValue) {
            core.setMessage("折價券低消小於折價券面額");
            core.setSuccessful(false);
        } else {
            try {
                final Date getTimeStart = Date.valueOf(req.getParameter("getTimeStart"));
                final Date getTimeOver = Date.valueOf(req.getParameter("getTimeOver"));
                final Date useTimeStart = Date.valueOf(req.getParameter("useTimeStart"));
                final Date useTimeOver = Date.valueOf(req.getParameter("useTimeOver"));
            } catch (IllegalArgumentException e) {
                core.setMessage("日期格式異常");
                core.setSuccessful(false);
            }

        }



    }
}

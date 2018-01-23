package com.myappilication.xpress.finjan2017.webservice;

import com.google.android.gms.auth.TokenData;
import com.myappilication.xpress.finjan2017.ccavenue.CcavenueReq;
import com.myappilication.xpress.finjan2017.ccavenue.CcavenueResponse;
import com.myappilication.xpress.finjan2017.mcqevalutiontest.McqTestReq;
import com.myappilication.xpress.finjan2017.mcqevalutiontest.McqTestResp;
import com.myappilication.xpress.finjan2017.menulist.mycamsinfo.MyCamResponse;
import com.myappilication.xpress.finjan2017.menulist.mycamsschemelist.SchemeListResponse;
import com.myappilication.xpress.finjan2017.models.login.CourseList.CourseListReq;
import com.myappilication.xpress.finjan2017.models.login.CourseList.CourseListResponse;
import com.myappilication.xpress.finjan2017.models.login.DownloadFinjan.DownloadFinjanReq;
import com.myappilication.xpress.finjan2017.models.login.DownloadFinjan.DownloadFinjanResponse;
import com.myappilication.xpress.finjan2017.models.login.FinjanVideo.FinjanReq;
import com.myappilication.xpress.finjan2017.models.login.FinjanVideo.FinjanResponse;

import com.myappilication.xpress.finjan2017.models.login.Result.ResultReq;
import com.myappilication.xpress.finjan2017.models.login.Result.ResultResponse;
import com.myappilication.xpress.finjan2017.models.login.VideoList.VideoListReq;
import com.myappilication.xpress.finjan2017.models.login.VideoList.VideoListResponse;
import com.myappilication.xpress.finjan2017.models.login.availablecourses.AvailableCoursesReq;
import com.myappilication.xpress.finjan2017.models.login.availablecourses.AvailableCoursesResponse;
import com.myappilication.xpress.finjan2017.models.login.changepassword.ChangePasswordreq;
import com.myappilication.xpress.finjan2017.models.login.changepassword.Changepasswordresp;
import com.myappilication.xpress.finjan2017.models.login.completemodpushtoserver.CompletemodResponse;
import com.myappilication.xpress.finjan2017.models.login.completemodpushtoserver.Completemodreq;
import com.myappilication.xpress.finjan2017.models.login.couponbasedcourses.CouponBSReq;
import com.myappilication.xpress.finjan2017.models.login.couponbasedcourses.CouponBSResponse;
import com.myappilication.xpress.finjan2017.models.login.dashboard.DashboardResponse;
import com.myappilication.xpress.finjan2017.models.login.dashboard.DashboradReq;
import com.myappilication.xpress.finjan2017.models.login.download.DownloadReq;
import com.myappilication.xpress.finjan2017.models.login.download.DownloadResponse;
import com.myappilication.xpress.finjan2017.models.login.evalution.EvalutionReq;
import com.myappilication.xpress.finjan2017.models.login.evalution.EvalutionResponse;
import com.myappilication.xpress.finjan2017.models.login.faq.faqreq;
import com.myappilication.xpress.finjan2017.models.login.faq.faqresp;
import com.myappilication.xpress.finjan2017.models.login.faqfulllist.faqfulllistreq;
import com.myappilication.xpress.finjan2017.models.login.faqfulllist.faqfulllistresp;
import com.myappilication.xpress.finjan2017.models.login.feedbackQAreq.FBQuesAnsReq;
import com.myappilication.xpress.finjan2017.models.login.feedbackQAreq.FBQuesResponse;
import com.myappilication.xpress.finjan2017.models.login.feedbackquestion.FeedbackResponse;
import com.myappilication.xpress.finjan2017.models.login.forget.forgotreq;
import com.myappilication.xpress.finjan2017.models.login.forget.forgotresp;
import com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon.UserAlreadyCouponReq;
import com.myappilication.xpress.finjan2017.models.login.isusralreadygetcoupon.UserAlreadyCouponRes;
import com.myappilication.xpress.finjan2017.models.login.login.loginreq;
import com.myappilication.xpress.finjan2017.models.login.login.loginresp;
import com.myappilication.xpress.finjan2017.models.login.modulelist.ListOfModuleReq;
import com.myappilication.xpress.finjan2017.models.login.modulelist.ListOfModuleResponse;
import com.myappilication.xpress.finjan2017.models.login.mycamssetting.MycamSettingResponse;
import com.myappilication.xpress.finjan2017.models.login.mycamstransactiondetails.Transactionreq;
import com.myappilication.xpress.finjan2017.models.login.mycamstransactiondetails.Transactionresponse;
import com.myappilication.xpress.finjan2017.models.login.newfaqcategorylist.NewFaqCategoryReq;
import com.myappilication.xpress.finjan2017.models.login.newfaqcategorylist.NewFaqCategoryResponse;
import com.myappilication.xpress.finjan2017.models.login.newfaqmoduleweb.NewFaqRequest;
import com.myappilication.xpress.finjan2017.models.login.newfaqmoduleweb.NewFaqResponse;
import com.myappilication.xpress.finjan2017.models.login.otpVerification.OtpResendReq;
import com.myappilication.xpress.finjan2017.models.login.otpVerification.OtpResendResponse;
import com.myappilication.xpress.finjan2017.models.login.otpVerification.OtpVerificationReq;
import com.myappilication.xpress.finjan2017.models.login.otpVerification.OtpVerificationResponse;
import com.myappilication.xpress.finjan2017.models.login.profileedit.profilereq;
import com.myappilication.xpress.finjan2017.models.login.profileedit.profileresp;
import com.myappilication.xpress.finjan2017.models.login.profileupdate.profileupdatereq;
import com.myappilication.xpress.finjan2017.models.login.profileupdate.profileupdateresp;
import com.myappilication.xpress.finjan2017.models.login.searchfaq.searchreq;
import com.myappilication.xpress.finjan2017.models.login.searchfaq.searchresp;
import com.myappilication.xpress.finjan2017.models.login.settings.settingsreq;
import com.myappilication.xpress.finjan2017.models.login.settings.settingsresp;
import com.myappilication.xpress.finjan2017.models.login.userreg.UserRegReq;
import com.myappilication.xpress.finjan2017.models.login.userreg.UserRegResponse;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by sureshmano on 3/7/2017.
 */

public interface RxApi {

    @POST("/login")
    void Login(@Body loginreq body, Callback<loginresp> callback);

    @POST("/getUsersScores")
    void submit(@Header("Authorization") String authToken, @Body McqTestReq body, Callback<McqTestResp> callback);


    @POST("/editProfile")
   void Editprofile(@Header("Authorization") String authToken,@Body profilereq body, Callback<profileresp> callback);

    @POST("/updateProfile")
    void Updateprofile(@Header("Authorization") String authToken,
                       @Body profileupdatereq body,Callback<profileupdateresp> callback);


    @POST("/userFaq")
    void userFaq(@Header("Authorization") String authToken, @Body faqreq body, Callback<faqresp> callback);

    @POST("/getSearchFieldsForFaq")
    void Searchview(@Header("Authorization") String authToken, @Body searchreq body, Callback<faqresp> callback);

    @POST("/forgotPassword")
    void ForgetPass(@Body forgotreq body, Callback<forgotresp> callback);

    @POST("/userDashboard")
    void DashBoard(@Header("Authorization") String authToken, @Body DashboradReq body, Callback<DashboardResponse> callback);
    @POST("/getManageMcq")
    void Evalution(@Header("Authorization") String authToken, @Body EvalutionReq body, Callback<EvalutionResponse> callback);
    @POST("/downloadVideo")
    void Download(@Header("Authorization") String authToken, @Body DownloadReq body, Callback<DownloadResponse> callback);
    @POST("/getUsersScores")
    void Result(@Body ResultReq body, Callback<ResultResponse> callback);
    @POST("/getUserFinjanvideo")
    void Finjan(@Header("Authorization") String authToken, @Body FinjanReq body, Callback<FinjanResponse> callback);
    @POST("/finjanVideo")
    void DownloadFinjan(@Header("Authorization") String authToken, @Body DownloadFinjanReq body, Callback<DownloadFinjanResponse> callback);
    @POST("/changePassword")
    void Changepassword(@Header("Authorization") String authToken,
                        @Body ChangePasswordreq body, Callback<Changepasswordresp> callback);
    @POST("/courseModule")
    void finListOfModule(@Header("Authorization") String authToken,
                         @Body ListOfModuleReq body, Callback<ListOfModuleResponse> callback);


    @POST("/userCourseList")
    void CourseList(@Header("Authorization") String authToken, @Body CourseListReq body, Callback<CourseListResponse> callback);


    @POST("/moduleBasedListVideo")
    void VideoList(@Header("Authorization") String authToken, @Body VideoListReq body, Callback<VideoListResponse> callback);


   @POST("/userSignup")
   void userReg(@Body UserRegReq body, Callback<UserRegResponse> callback);


   @POST("/verificationOtp")
   void verifOtp(@Header("Authorization") String authToken, @Body OtpVerificationReq body, Callback<OtpVerificationResponse> callback);

   @POST("/resendOtp")
   void otpResend(@Header("Authorization") String authToken, @Body OtpResendReq body, Callback<OtpResendResponse> callback);

    @POST("/useravailableCourse")
    void availableCourseslist(@Header("Authorization") String authToken, @Body AvailableCoursesReq body, Callback<AvailableCoursesResponse> callback);

    @POST("/CouponbasedCourse")
    void couponbasedCourse(@Header("Authorization") String authToken, @Body CouponBSReq body, Callback<CouponBSResponse> callback);

    @POST("/getfeedbackQuestions")
    void getfeedbkQuestions(@Header("Authorization") String authToken, Callback<FeedbackResponse> callback);

    @POST("/getfeedbackQuesAnswers")
    void getfeedbkQuesAnswer(@Header("Authorization") String authToken, @Body FBQuesAnsReq body, Callback<FBQuesResponse> callback);


    @POST("/getCategoryCourseQuestions")
    void getNewFaqModuleQs(@Header("Authorization") String authToken, @Body NewFaqRequest body,
                           Callback<NewFaqResponse> callback);

    @POST("/getCategoryList")
    void getNewFaqCategory(@Header("Authorization") String authToken, @Body NewFaqCategoryReq body,
                           Callback<NewFaqCategoryResponse> callback);

    @POST("/getSearchFieldsForQus")
    void getSearchFieldsForQus(@Header("Authorization") String authToken, @Body faqfulllistreq body,
                           Callback<faqfulllistresp> callback);

// getUserCourseDetails    getCourseForUser
    @POST("/getCourseForUser")
    void getUserCourseDtls(@Header("Authorization") String authToken, @Body UserAlreadyCouponReq body,
                               Callback<UserAlreadyCouponRes> callback);


 /*@POST("/getUserCourseDetails")
 void getUserCourseDtls1(@Body UserAlreadyCouponReq body,
                        Callback<UserAlreadyCouponRes> callback);*/

    @POST("/updateUserFinishedCourse")
    void getUserFinishedCourse(@Header("Authorization") String authToken, @Body Completemodreq body,
                           Callback<CompletemodResponse> callback);

    @POST("/getCamsAPISetting")
    void myCamsInfo(Callback<MyCamResponse> callback);

    @POST("/listSchemes")
    void schemeList(Callback<SchemeListResponse> callback);

    @POST("/getCamsAPISettings")
    void mycamssetting(@Header("Authorization") String authToken, Callback<MycamSettingResponse> callback);

    @POST("/getCamsTransactionDetails")
    void transactiondetails(@Header("Authorization") String authToken, @Body Transactionreq body,
                            Callback<Transactionresponse> callback);

    @POST("/ccAvenueUpdate")
    void ccavenueres(@Header("Authorization") String authToken, @Body CcavenueReq body,
                         Callback<CcavenueResponse> callback);
}





   /* @POST("/queryService/myVideosToPlay")
    void MyPrivateLists(@Header("authtoken") String authToken, @Body PrivatePlayListReq body, Callback<PlayListResp> callback);*/



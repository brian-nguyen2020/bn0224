package com.diy.rental.service;

import com.diy.rental.exception.CodeDescriptionError;
import com.diy.rental.exception.ErrorCodeEnum;
import com.diy.rental.exception.StatusCode400Exception;
import com.diy.rental.http.CheckoutRequest;
import com.diy.rental.http.RequestContext;
import com.diy.rental.http.ResponseEnvelope;
import com.diy.rental.http.ResultConstants;
import com.diy.rental.view.RentalAgreement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CheckoutService {
    @Value("${min.rental.day}")
    int minRentalDay;
    @Value("${min.discount.percent}")
    int minDiscountPercent;
    @Value("${max.discount.percent}")
    int maxDiscountPercent;
    private RequestContext requestContext;
    private RentalAgreementService rentalAgreementService;

    @Autowired
    public CheckoutService(RequestContext requestContext, RentalAgreementService rentalAgreementService) {
        this.requestContext = requestContext;
        this.rentalAgreementService = rentalAgreementService;
    }
    public ResponseEnvelope doCheckout(CheckoutRequest checkoutRequest) throws  Exception{
        requestContext.setBody(checkoutRequest);
        List<CodeDescriptionError> errorList = this.validateCheckoutData(checkoutRequest);
        if(errorList.size() > 0) {
            throw new StatusCode400Exception( errorList);
        }

        RentalAgreement rentalAgreement = rentalAgreementService.createRentalAgreement(checkoutRequest);
        rentalAgreementService.printRentalAgreement(rentalAgreement);
        return new ResponseEnvelope(ResultConstants.SUCCESS, rentalAgreement);
    }


    private List<CodeDescriptionError> validateCheckoutData(CheckoutRequest checkoutRequest) {
        List<CodeDescriptionError> errorList = new ArrayList<>();
        if (checkoutRequest.getRentalDays() < minRentalDay) {
            errorList.add(new CodeDescriptionError(ErrorCodeEnum.INVALID_RENTAL_DAYS.getCode(), ErrorCodeEnum.INVALID_RENTAL_DAYS.getDescription()));
        }
        if((checkoutRequest.getDiscountPercent() < minDiscountPercent) || (checkoutRequest.getDiscountPercent() > maxDiscountPercent)) {
            errorList.add(new CodeDescriptionError(ErrorCodeEnum.INVALID_DISCOUNT_PERCENT.getCode(), ErrorCodeEnum.INVALID_DISCOUNT_PERCENT.getDescription()));

        }
        return errorList;
    }

}

package com.chanctn.demoomise.controller

import co.omise.Client
import co.omise.models.Charge
import co.omise.requests.Request
import com.chanctn.demoomise.dto.ChargeDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class ChargeController {

    @PostMapping("/charge")
    fun charge(@RequestBody chargeDto: ChargeDto): ResponseEntity<Void> {
        try {
            println("chargeDto.... $chargeDto")
            if (chargeDto.omiseToken != null) {
                val client = Client.Builder()
                    .publicKey("<YOUR_PUBLIC_KEY>")
                    .secretKey("<YOUR_PRIVATE_KEY>")
                    .build()
                val request: Request<Charge> = Charge.CreateRequestBuilder()
                    .amount(50000) // 1,000 THB
                    .capture(true)
                    .currency("thb")
                    .card(chargeDto.omiseToken)
                    .build()
                val charge = client.sendRequest<Charge, Request<Charge>>(request)
                println("created charge id =  : ${charge.id} with capture = true")
                println("isPaid := ${charge.isPaid}")
                // check getChargeRequest later
//                val getChargeRequest = Charge.GetRequestBuilder(charge.id).build()
//                val getChargeRequestResponse: Charge = client.sendRequest(getChargeRequest)
//
//                println("charge id =  : ${charge.id} getReqId:= ${getChargeRequestResponse.id}," +
//                        " card =: ${getChargeRequestResponse.card}" +
//                        " amount =:${getChargeRequestResponse.amount}," +
//                        " isPaid =: ${getChargeRequestResponse.isPaid}," +
//                        " customer =: ${getChargeRequestResponse.customer}," +
//                        " authorizeUri =: ${getChargeRequestResponse.authorizeUri}," +
//                        " branch =: ${getChargeRequestResponse.branch}," +
//                        " currency =: ${getChargeRequestResponse.currency}," +
//                        " status =: ${getChargeRequestResponse.status}," +
//                        " paidAt =: ${getChargeRequestResponse.paidAt},"
//                )

            }
            return ResponseEntity.ok().build()
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }

    }
}
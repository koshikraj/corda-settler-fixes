package com.r3.corda.finance.obligation.oracle.flows

import co.paralleluniverse.fibers.Suspendable
import com.r3.corda.finance.obligation.types.FxRateRequest
import com.r3.corda.finance.obligation.flows.AbstractGetFxRate
import com.r3.corda.finance.obligation.oracle.services.FxRateService
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.flows.InitiatedBy
import net.corda.core.utilities.unwrap

@InitiatedBy(AbstractGetFxRate::class)
class ReceiveFxRateRequest(val otherSession: FlowSession) : FlowLogic<Unit>() {

    @Suspendable
    override fun call() {
        val request = otherSession.receive<FxRateRequest>().unwrap { it }
        val fxRateService = serviceHub.cordaService(FxRateService::class.java)
        val response = fxRateService.getRate(request)
        otherSession.send(response)
    }

}
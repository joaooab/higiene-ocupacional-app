package br.com.joaoov.usecase

interface CheckBillingUseCase {

    operator fun invoke(): Boolean
}

class CheckBillingUseCaseImpl:CheckBillingUseCase {

    override fun invoke() = false
}
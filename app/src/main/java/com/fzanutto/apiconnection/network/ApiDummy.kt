package com.fzanutto.apiconnection.network

import com.fzanutto.apiconnection.model.CheckIn
import com.fzanutto.apiconnection.model.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class ApiDummy: ApiConnection {

    private val events = arrayListOf(
        Event("Feira de adoção de animais na Redenção", Date(), "http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png", -51.2146267, -30.0392981, 1,"Vamos ajudar !!\n\nSe você tem n...",  29.99),
        Event("Doação de roupas", Date(), "http://fm103.com.br/wp-content/uploads/2017/07/campanha-do-agasalho-balneario-camboriu-2016.jpg", -51.2148497, -30.037878, 2,"O Patas Dadas estará na Redenção, ness...",  59.99),
        Event("Doação de roupas", Date(), "http://fm103.com.br/wp-content/uploads/2017/07/campanha-do-agasalho-balneario-camboriu-2016.jpg", -51.2148497, -30.037878, 2,"O Patas Dadas estará na Redenção, ness...",  59.99),
        Event("Doação de roupas", Date(), "https://i2.wp.com/assentopublico.com.br/wp-content/uploads/2017/07/Feira-de-alimentos-org%C3%A2nicos-naturais-e-artesanais-ganha-um-novo-espa%C3%A7o-em-Ribeir%C3%A3o.jpg", -51.2148497, -30.037878, 2,"O Patas Dadas estará na Redenção, ness...",  59.99),
        Event("Doação de roupas", Date(), "https://i2.wp.com/assentopublico.com.br/wp-content/uploads/2017/07/Feira-de-alimentos-org%C3%A2nicos-naturais-e-artesanais-ganha-um-novo-espa%C3%A7o-em-Ribeir%C3%A3o.jpg", -51.2148497, -30.037878, 2,"O Patas Dadas estará na Redenção, ness...",  59.99),
        Event("Doação de roupas", Date(), "http://fm103.com.br/wp-content/uploads/2017/07/campanha-do-agasalho-balneario-camboriu-2016.jpg", -51.2148497, -30.037878, 2,"O Patas Dadas estará na Redenção, ness...",  59.99),
        Event("Doação de roupas", Date(), "https://static.wixstatic.com/media/579ac9_81e9766eaa2741a284e7a7f729429022~mv2.png", -51.2148497, -30.037878, 2,"O Patas Dadas estará na Redenção, ness...",  59.99),
        Event("Doação de roupas", Date(), "http://fm103.com.br/wp-content/uploads/2017/07/campanha-do-agasalho-balneario-camboriu-2016.jpg", -51.2148497, -30.037878, 2,"O Patas Dadas estará na Redenção, ness...",  0.0),
        Event("Doação de roupas", Date(), "http://fm103.com.br/wp-content/uploads/2017/07/campanha-do-agasalho-balneario-camboriu-2016.jpg", -51.2148497, -30.037878, 2,"O Patas Dadas estará na Redenção, ness...",  59.99),
    )

    override fun getEventList(onSuccess: (events: List<Event>) -> Unit, onFailure: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(400)
            onSuccess(events)
        }
    }

    override fun getEventById(id: Int, onSuccess: (event: Event) -> Unit, onFailure: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(400)

            events.find { it.id == id }?.let {
                onSuccess(it)
            } ?: run {
                onFailure()
            }
        }
    }

    override fun sendCheckIn(
        checkIn: CheckIn,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        onSuccess()
    }
}
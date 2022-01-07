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
        Event("1 Feira de adoção de animais na Redenção", Date(), "http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png", -51.2146267, -30.0392981, 1,"Vamos ajudar !!\n\nSe você tem n...",  29.99),
        Event("2 Doação de roupas", Date(), "http://fm103.com.br/wp-content/uploads/2017/07/campanha-do-agasalho-balneario-camboriu-2016.jpg", -51.2148497, -30.037878, 2,"O Patas Dadas estará na Redenção, ness...",  59.99),
        Event("", null, "", Double.NaN, Double.NaN, 3,"O Patas Dadas estará na Redenção, ness...",  null),
        Event("4 Doação de roupas", null, "https://i2.wp.com/assentopublico.com.br/wp-content/uploads/2017/07/Feira-de-alimentos-org%C3%A2nicos-naturais-e-artesanais-ganha-um-novo-espa%C3%A7o-em-Ribeir%C3%A3o.jpg", -51.2148497, -30.037878, 4,"O Patas Dadas estará na Redenção, ness...",  59.99),
        Event("5 Doação de roupas", null, "https://i2.wp.com/assentopublico.com.br/wp-content/uploads/2017/07/Feira-de-alimentos-org%C3%A2nicos-naturais-e-artesanais-ganha-um-novo-espa%C3%A7o-em-Ribeir%C3%A3o.jpg", -51.2148497, -30.037878, 5,"O Patas Dadas estará na Redenção, ness...",  0.0),
        Event("6 Doação de roupas", Date(), "http://fm103.com.br/wp-content/uploads/2017/07/campanha-do-agasalho-balneario-camboriu-2016.jpg", Double.NaN, -30.037878, 6,"O Patas Dadas estará na Redenção, ness...",  null),
        Event("", Date(), "https://static.wixstatic.com/media/579ac9_81e9766eaa2741a284e7a7f729429022~mv2.png", -51.2148497, -30.037878, 7,"O Patas Dadas estará na Redenção, ness...",  59.99),
        Event("8 Doação de roupas", null, "http://fm103.com.br/wp-content/uploads/2017/07/campanha-do-agasalho-balneario-camboriu-2016.jpg", -51.2148497, -30.037878, 8,"O Patas Dadas estará na Redenção, ness...",  0.0),
        Event("9 Doação de roupas", Date(), "", -51.2148497, -30.037878, 9,"O Patas Dadas estará na Redenção, ness...",  59.99),
        Event("10 Doação de roupas", Date(), null, -51.2148497, -30.037878, 10,"O Patas Dadas estará na Redenção, ness...",  59.99),
    )

    override fun getEventList(onSuccess: (events: List<Event>) -> Unit,  onFailure: (error: String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(400)
            onSuccess(events)
        }
    }

    override fun getEventById(id: Int, onSuccess: (event: Event) -> Unit,  onFailure: (error: String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(400)

            events.find { it.id == id }?.let {
                onSuccess(it)
            } ?: run {
                onFailure("404")
            }
        }
    }

    override fun sendCheckIn(
        checkIn: CheckIn,
        onSuccess: () -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        onSuccess()
    }
}
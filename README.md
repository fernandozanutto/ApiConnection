
### Screenshots

<img src="https://user-images.githubusercontent.com/15229294/148566281-424ba074-67f1-4f39-a2a4-31599e42087b.jpg" alt="Home" width="200"/> &nbsp;
<img src="https://user-images.githubusercontent.com/15229294/148566280-01bf9e52-2ebe-4c4e-85ac-88c774102900.jpg" alt="Detalhes de evento" width="200"/> &nbsp;
<img src="https://user-images.githubusercontent.com/15229294/148566276-8548d329-c777-4b09-8317-f2e8b850bd15.jpg" alt="Detalhes de evento 2" width="200"/> &nbsp;
<img src="https://user-images.githubusercontent.com/15229294/148566266-78aa7b33-4f8c-4f59-9dbd-33fbebe23a81.jpg" alt="Envio de check-in" width="200"/> &nbsp;


### Arquitetura
Utilizada a arquitetura MVVM, separando as classes do projeto entre Models, Views e ViewModels

### Tecnologias e Bibliotecas utilizadas
- Kotlin
- Material Design
- LiveData
- Google Maps SDK
- Glide
- Volley (nenhum preferência específica em relação ao Retrofit, apenas quis utilizar uma biblioteca diferente para este projeto)
- SwipeRefreshLayout

### TODO (melhorias possíveis no projeto)
- [ ] Validação nos campos de nome e e-mail no formulário de check-in
- [X] Mensagem informando quando lista de eventos for vazia ou quando ocorreu um erro ao receber a lista.
- [ ] Persistência dos dados recebidos pela API para que o aplicativo não fique 100% dependente da internet.
- [ ] Implementação de testes unitários e de aceitação
- [X] Implementação de tema escuro

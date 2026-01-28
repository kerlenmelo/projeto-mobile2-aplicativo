# Aconselhamento Financeiro Pessoal

Este é um aplicativo Android nativo para gerenciamento de finanças pessoais. Ele permite que os usuários controlem suas receitas e despesas, visualizem o saldo atual e analisem seus gastos por meio de relatórios gráficos.
Alunos:
    - Rafael Marques
    - Kerlen Melo
    - Cláudio Farias

## Funcionalidades

- **Autenticação de Usuário:** Sistema de cadastro e login para acesso seguro.
- **Gerenciamento de Transações:** Adicione, edite e exclua transações financeiras (receitas ou despesas).
- **Dashboard Principal:**
    - Mensagem de boas-vindas personalizada.
    - Exibição do saldo total em tempo real.
    - Lista de transações recentes.
- **Relatórios Gráficos:** Visualize a distribuição de despesas por categoria em um gráfico de pizza.
- **Persistência de Dados:** As informações do usuário e suas transações são salvas localmente no dispositivo usando o Room.
- **Gerenciamento de Sessão:** O usuário permanece logado no aplicativo mesmo após fechá-lo.

## Tecnologias Utilizadas

- **Linguagem:** [Kotlin](https://kotlinlang.org/)
- **Interface de Usuário:** [Jetpack Compose](https://developer.android.com/jetpack/compose) para uma UI moderna e declarativa.
- **Arquitetura:** MVVM (Model-View-ViewModel) para uma estrutura organizada e testável.
- **Navegação:** [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) para gerenciar as telas do app.
- **Banco de Dados:** [Room](https://developer.android.com/training/data-storage/room) para persistência de dados local.
- **Gráficos:** [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) para a exibição dos relatórios.
- **Ciclo de Vida:** Componentes do [Android Jetpack Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle).

## Estrutura do Projeto

O projeto é organizado seguindo os princípios da arquitetura limpa e do MVVM:

- `data/`: Contém as classes relacionadas a dados.
    - `local/`: Gerenciamento do banco de dados Room (`AppDatabase`, `UserDao`, `TransactionDao`) e da sessão do usuário (`SessionManager`).
    - `model/`: As entidades do banco de dados (`UserModel`, `TransactionEntity`).
    - `repository/`: Repositórios que abstraem a fonte de dados (`TransactionRepository`, `UserRepository`).
- `ui/`: Contém os componentes de UI (telas) construídos com Jetpack Compose.
    - `home/`: Tela principal, adição e edição de transações.
    - `login/`: Telas de login e registro.
    - `reports/`: Tela de relatórios.
    - `theme/`: Tema visual do aplicativo.
- `viewmodel/`: ViewModels que contêm a lógica de negócio e expõem o estado para a UI.
- `MainActivity.kt`: A atividade principal que hospeda o grafo de navegação do Compose.

## Como Instalar e Rodar o Projeto

### Pré-requisitos

- [Android Studio](https.developer.android.com/studio) (versão mais recente recomendada).
- Emulador Android ou um dispositivo físico.

### Passos

1.  **Clonar o Repositório:**
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    ```

2.  **Abrir no Android Studio:**
    - Abra o Android Studio.
    - Selecione "Open an Existing Project" e navegue até a pasta onde você clonou o projeto.

3.  **Sincronizar o Gradle:**
    - O Android Studio deve iniciar a sincronização do Gradle automaticamente. Aguarde até que todas as dependências sejam baixadas e o projeto seja construído.

4.  **Executar o Aplicativo:**
    - Selecione um emulador ou conecte um dispositivo físico.
    - Pressione o botão "Run" (ícone de play verde) na barra de ferramentas do Android Studio ou use o atalho `Shift + F10`.

O aplicativo será instalado e iniciado no dispositivo selecionado. Você pode começar criando uma nova conta na tela de registro e, em seguida, fazer login para acessar a tela principal.

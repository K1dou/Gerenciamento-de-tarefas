# Gerenciamento de Tarefas

![Java](https://img.shields.io/badge/Java-100%25-orange)

Bem-vindo ao repositório do **Gerenciamento de Tarefas**! Este projeto foi desenvolvido com o objetivo de facilitar o controle e organização de tarefas, permitindo que os usuários acompanhem seus compromissos e melhorem sua produtividade.

## 📝 Funcionalidades

- 📋 **Cadastro de Tarefas**: Adicione tarefas com detalhes como título, descrição, prazo e prioridade.
- ✅ **Atualização de Status**: Marque tarefas como concluídas e acompanhe seu progresso.
- 🔍 **Busca e Filtros**: Encontre tarefas rapidamente com filtros por status, data ou prioridade.
- 📊 **Relatórios**: Visualize estatísticas de produtividade.

## 🚀 Tecnologias Utilizadas

Este projeto foi desenvolvido utilizando as seguintes tecnologias:

- **Java**: Linguagem principal do projeto.
- **JUnit**: Framework utilizado para testes unitários, garantindo a confiabilidade do código.
- **Mockito**: Ferramenta usada para criação de mocks em testes, facilitando a simulação de dependências.

## ✅ Testes Automatizados

Os testes têm um papel fundamental neste projeto para garantir a qualidade do código e prevenir bugs. Os seguintes tipos de testes foram implementados:

- **Testes Unitários**: Com o JUnit, cobrimos as principais funcionalidades, como criar, atualizar e remover tarefas.
- **Simulação de Dependências**: Utilizamos o Mockito para simular comportamentos de classes externas e focar no teste da lógica interna.
- **Cobertura de Código**: Garantimos uma cobertura ampla para as partes críticas do sistema.

Para rodar os testes, use o seguinte comando no terminal:
```bash
./gradlew test
```
Ou configure sua IDE para executar os testes diretamente.

## 📂 Estrutura do Projeto

```bash
Gerenciamento-de-tarefas/
├── src/              # Código-fonte principal
├── tests/            # Testes automatizados com JUnit e Mockito
└── docs/             # Documentação do projeto
```

## ⚙️ Como Executar

Siga os passos abaixo para rodar o projeto localmente:

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/K1dou/Gerenciamento-de-tarefas.git
   ```
2. **Compile o projeto**:
   Utilize sua IDE ou o terminal:
   ```bash
   javac -d bin src/**/*.java
   ```
3. **Execute o programa**:
   ```bash
   java -cp bin Main
   ```

## 🤝 Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para enviar pull requests ou abrir issues para reportar problemas e sugerir melhorias.

## 📧 Contato

Se quiser saber mais sobre este projeto ou discutir oportunidades, entre em contato:

- **Autor**: [K1dou](https://github.com/K1dou)
- **Email**: [seu-email@email.com](mailto:seu-email@email.com)
- **LinkedIn**: [Seu Perfil](https://www.linkedin.com/in/seu-perfil)

---

Esse README agora destaca o uso de JUnit e Mockito, o que pode impressionar recrutadores interessados em boas práticas de testes. Se precisar de mais ajustes, é só avisar!

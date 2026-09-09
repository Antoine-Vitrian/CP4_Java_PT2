# Sistema de gestão de delegacia

Projeto Java 17 com JDBC e DAOs para cadastro, listagem, busca, alteração e exclusão de policiais.

## Executar sem instalar Oracle

O banco padrão é o H2 embarcado: roda junto com o Java e salva os dados em `data/delegacia.mv.db`. As tabelas são criadas automaticamente. No primeiro uso, uma delegacia de demonstração é criada com ID **1**, que pode ser usado no cadastro de policiais.

No IntelliJ IDEA, abra o `pom.xml` como projeto Maven, selecione o JDK 17, recarregue as dependências Maven e execute `br.com.fiap.delegacia.Main`. Use a raiz do projeto como diretório de trabalho.

Com Java 17 e Maven no terminal:

```sh
mvn compile exec:java
```

O primeiro carregamento das dependências precisa de internet. Os dados locais e arquivos da IDE não são enviados ao GitHub. O JAR em `lib/` não é necessário: os drivers são obtidos pelo Maven.

## Usar Oracle opcionalmente

Configure as variáveis de ambiente na execução do IntelliJ ou no terminal. Exemplo em PowerShell:

```powershell
$env:DB_URL = 'jdbc:oracle:thin:@servidor:1521/servico'
$env:DB_USER = 'seu_usuario'
$env:DB_PASSWORD = 'sua_senha'
mvn compile exec:java
```

É necessário ter acesso a um servidor Oracle com as tabelas `delegacia` e `policial` e geração automática dos IDs já configuradas. O servidor pode ser remoto; não precisa estar instalado neste computador. A criação automática de tabelas é exclusiva do H2. Não coloque credenciais no código.

O H2 permite usar o sistema localmente, mas não substitui a validação em Oracle caso esse banco seja exigido na atividade.

Referência: [documentação do H2 sobre execução embarcada](https://h2database.com/html/features.html#embedded_databases).

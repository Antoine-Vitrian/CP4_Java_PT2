# Sistema de gestão de delegacia

Projeto Java 17 com JDBC e DAOs para cadastro, listagem, busca, alteração e exclusão de policiais. O driver está em `lib/jdbc17.jar` — sem Maven.

## Configuração

1. Edite `src/main/java/br/com/fiap/delegacia/database/ConnectionFactory.java` e troque `RMXXXXXX` e `XXXXXX` pelo seu RM e senha do Oracle FIAP.
2. O driver JDBC está em `lib/jdbc17.jar`.
3. Crie as tabelas no Oracle com o script `sql/delegacia.sql`, se ainda não existirem.

Também é possível definir `DB_URL`, `DB_USER` e `DB_PASSWORD` por variável de ambiente, sem alterar o código.

## Como executar

No IntelliJ, abra a pasta do projeto (não use Maven), marque `lib/jdbc17.jar` como dependência do módulo e execute `br.com.fiap.delegacia.Main`. Use JDK 17.

Pelo terminal (Windows), a partir da raiz do projeto:

```powershell
javac -encoding UTF-8 -cp lib/jdbc17.jar -d out (Get-ChildItem -Recurse src/main/java/*.java).FullName
java -cp "out;lib/jdbc17.jar" br.com.fiap.delegacia.Main
```

É necessário estar na rede/VPN da FIAP para alcançar `oracle.fiap.com.br`.

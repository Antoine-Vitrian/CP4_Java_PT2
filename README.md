# Sistema de gestão de delegacia

Projeto Java 17 com JDBC e DAOs para cadastro, listagem, busca, alteração e exclusão de policiais. O driver está em `lib/jdbc17.jar` — sem Maven.

## Configuração

1. Edite `src/main/java/br/com/fiap/delegacia/database/ConnectionFactory.java` e troque `RMXXXXXX` e `XXXXXX` pelo seu RM e senha do Oracle FIAP.
2. O driver JDBC está em `lib/jdbc17.jar`.
3. Crie as tabelas no Oracle com o script `sql/delegacia.sql`, se ainda não existirem.

Também é possível definir `DB_URL`, `DB_USER` e `DB_PASSWORD` por variável de ambiente, sem alterar o código.

## Como executar

No IntelliJ:

1. Abra a pasta `delegacia` (File → Open).
2. Selecione JDK 17 em File → Project Structure → Project.
3. Abra `src/main/java/br/com/fiap/delegacia/Main.java`.
4. Clique com o botão direito → **Run 'Main.main()'**, ou use a configuração **Main** no canto superior direito.

É necessário estar na rede/VPN da FIAP para alcançar `oracle.fiap.com.br`.

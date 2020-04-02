# DOCUMENTAÇÃO

<h2>Passos</h2>

<ul>
	<li>Cadastrar as pessoas (principalmente, o administrador com perfil de <b>admin</b>)</li>
	<li>Autenticar com uma pessoa</li>
	<li>Criar os ingredientes (só admin tem permissão)</li>
	<li>Criar os lanches (só admin tem permissão)</li>
	<li>Fazer um pedido com um usuário comum</li>
	<li>Fazer listagens</li>
</ul>

<br />

* Os exemplos de estruturas json e descrição das endpoints estão disponibilizadas abaixo. 

<hr />
<h2>Autenticação</h2>

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/oauth/token      <b>Method: POST</b>
<br /> <b>Descrição:</b> Obter o token de acesso

<h3>Pelo Postman</h3>
<h4>1. Passo</h4>
<p>Precisa escolher a opção 'Basic Auth' da aba 'Authorization' e informar o client-id e o client-secret definidos no application.properties e depois clicar no botão 'Preview Request'. Depois disso, será adicionada uma key 'Authorization' no header com o valor 'Basic ...' </p>

```
	client-id: startup
	client-secret: 12345678
```

<h4>2. Passo</h4>
<p>Passar um formulário de três campos no body com o tipo x-www-form-urlencoded. Os campos e os valores estão apresentados abaixo: (São usuário e senha de uma determinada pessoa que foi cadastrada)</p>

```
	campo: username, valor: admin, 
	campo: password, valor: 123456, 
	campo: grant_type, valor: password
```

<h4>3. Passo</h4>
<p>O resultado bem sucedido será o retorno deste objeto: </p>

* O importante é o valor de 'access_token' que você precisa copiá-lo.
```
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidGVzdGp3dHJlc291cmNlaWQiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTg1ODUxMjUxLCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiI0ZmU0OTM2Ni01MmYwLTQ4YzItYmI5Yy0wMmM1MzljZGZiMjciLCJjbGllbnRfaWQiOiJzdGFydHVwIn0.un2YRHspbr1t0tBiNaWxbyEbGHMrX0ZHUOxEvp3rAn4",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "read write",
    "jti": "4fe49366-52f0-48c2-bb9c-02c539cdfb27"
}
```

<h4>4. Passo</h4>
<ul>
	<li>Escolha uma requisição que deseja fazer</li>
	<li>Escolha a opção 'Bearer Token' na aba 'Authorization'</li>
	<li>Copia e cola o valor apresentado no atributo 'access_token'</li>
	<li>Insira o token no campo disponível ao lado</li>
	<li>Clique em 'Preview Request' (Será adicionado o valor 'Bearer '+ token no header)</li>
	<li>Passar algum objeto no body se necessário</li>
<ul>

<hr />
<h2>Consultas</h2>

<h3>Pessoas</h3>

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/person      <b>Method: GET</b>
<br /> <b>Descrição:</b> Lista todas as pessoas.
<br /> <b>Permissão: </b> Somente o administrador.

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/person/<b>{id}</b>      <b>Method: GET</b>
<br /> <b>Descrição:</b> Lista uma pessoa específica.
<br /> <b>Permissão: </b> O administrador pode listar qualquer pessoa e o usuário comum apenas pode ver os seus própros dados.

<h3>Ingredientes</h3>

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/ingredients      <b>Method: GET</b>
<br /> <b>Descrição:</b> Lista todos os ingredientes.
<br /> <b>Permissão: </b> Somente o administrador.

<h3>Lanches</h3>
<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/snacks      <b>Method: GET</b>
<br /> <b>Descrição:</b> Lista todos os lanches.
<br /> <b>Permissão: </b> Somente o administrador.

<h3>Pedidos</h3>

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/orders      <b>Method: GET</b>
<br /> <b>Descrição:</b> Lista de todos os pedidos.
<br /> <b>Permissão: </b> Somente o administrador tem acesso.

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/orders/<b>{id}</b>      <b>Method: GET</b>
<br /> <b>Descrição:</b> Lista um determinado pedido através do código. 
<br /> <b>Permissão: </b> O administrador pode acessar qualquer pedido através do código e o usuário comum só pode acessar os seus pedidos individualmente. 

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/orders/my      <b>Method: GET</b>
<br /> <b>Descrição:</b> Lista os pedidos do usuário autenticado. 
<br /> <b>Permissão: </b> O administrador e o usuário comum têm acessos. 

<hr />
<h2>Cadastros</h2>

<h3>Cadastrar Pessoas</h3>

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/person      <b>Method: POST</b>
<br /><b>Descrição:</b> Cadastrar uma lista de pessoas.
<br /> <b>Permissão: </b> Administrador e o usuário comum.

Exemplo: 
```
[
	{
	    "fullName": "Administrador",
	    "username": "admin",
	    "password": "123456",
	    "profile": "CUSTOMER"		
	},
	{
	    "fullName": "David Sales",
	    "username": "david",
	    "password": "david",
	    "profile": "CUSTOMER"		
	},
	{
	    "fullName": "João Vinicius",
	    "username": "joao",
	    "password": "joao",
	    "profile": "CUSTOMER"
	},
	{
	    "fullName": "Maria Alves",
	    "username": "maria",
	    "password": "maria",
	    "profile": "CUSTOMER"
	}	
]
```

<h3>Atualiza pessoa</h3>

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/person/<b>{id}</b>      <b>Method: PUT</b>
<br /><b>Descrição:</b> Atualiza os dados de uma pessoa específica.
<br /> <b>Permissão: </b> Administrador pode atualizar dados de qualquer pessoa e o usuário comum só pode alterar os seus próprios dados.

Exemplo: 
```
	{
	    "fullName": "João Vinicius",
	    "username": "joao",
	    "password": "joao",
	    "profile": "CUSTOMER"
	}
```

<h3>Exclui pessoa</h3>

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/person/<b>{id}</b>      <b>Method: DELETE</b>
<br /><b>Descrição:</b> Excluir uma pessoa específica.
<br /> <b>Permissão: </b> Administrador pode excluir qualquer pessoa e o usuário comum só pode excluir ele mesmo.

<hr />
<h3>Salvar ingredientes</h3>
  
<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/ingredients      <b>Method: POST</b>
<br />
<b>Descrição:</b> Salvar novo ingrediente
<br /> <b>Permissão: </b> Somente o administrador.

```
[
	{
		"name":"Alface",
		"price": 0.4
	},
	{
		"name":"Bacon",
		"price": 2
	},
	{
		"name":"Hambúrguer",
		"price": 3
	},
	{
		"name":"Ovo",
		"price": 0.8
	},
	{
		"name":"Queijo",
		"price": 1.5
	}
]
```

<b>Descrição:</b> Atualizar o registro do ingrediente do código 6 (Passando o id)

```
  [
    {
      "id": 6,
      "name":"teste",
      "price": 3
    }
  ]
```

<h3>Excluir ingrediente</h3>

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/ingredients/<b>{id}</b>      <b>Method: DELETE</b>
<br />
<b>Descrição:</b> Exclui apenas um ingrediente. 
<br /> <b>Permissão: </b> Somente o administrador.
		

<hr />
<h3>Salvar lanches</h3>

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/snacks      <b>Method: POST</b>
<br /><b>Descrição:</b> Cadastra uma lista de lanches.
<br /><b>Permissão: </b> Somente o administrador.

Exemplo de estrutura (* Todos os atributos são obrigatórios): 
```
[
	{
		"name": "X-Bacon",
		"isCustom": false,
		"items": [
			{
				"ingredient": {
					"id": 2,
					"name": "Bacon",
					"price": 2	
				},
				"piece": 1
			},
			{
				"ingredient": {
					"id": 3,
					"name": "Hambúrguer",
					"price": 3
				},
				"piece": 1
			},
			{
				"ingredient": {
					"id": 5,
					"name": "Queijo",
					"price": 1.50
				},
				"piece": 1
			}
		]
	},
	{
		"name": "X-Burger",
		"isCustom": false,
		"items": [
			{
				"ingredient":{
					"id": 3,
					"name": "Hambúrguer",
					"price": 3	
				},
				"piece": 1
			},
			{
				"ingredient":{
					"id": 5,
					"name": "Queijo",
					"price": 1.50	
				},
				"piece": 1
			}
		]
	},
	{
		"name": "X-Egg",
		"isCustom": false,
		"items": [
			{
				"ingredient": {
					"id": 3,
					"name": "Hambúrguer",
					"price": 3	
				},
				"piece": 1
			},
			{
				"ingredient": {
					"id": 4,
					"name": "Ovo",
					"price": 0.80	
				},
				"piece": 1
			}, 
			{
				"ingredient": {
					"id": 5,
					"name": "Queijo",
					"price": 1.50	
				},
				"piece": 1
			}
		]
	},
	{
		"name": "X-Egg Bacon",
		"isCustom": false,
		"items": [
			{
				"ingredient": {
					"id": 2,
					"name": "Bacon",
					"price": 2
				},
				"piece": 1
			}, 
			{
				"ingredient": {
					"id": 3,
					"name": "Hambúrguer",
					"price": 3	
				},
				"piece": 1
			},
			{
				"ingredient": {
					"id": 4,
					"name": "Ovo",
					"price": 0.80
				},
				"piece": 1
			}, 
			{
				"ingredient": {
					"id": 5,
					"name": "Queijo",
					"price": 1.50	
				},
				"piece": 1
			}
		]
	}
]
```

<h3>Exclui lanche</h3>

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/snacks/<b>{id}</b>      <b>Method: DELETE</b>
<br /><b>Descrição:</b> Exclui um determinado lanche por meio do código.
<br /> <b>Permissão: </b> Somente o administrador.

<hr />
<h3>Salvar pedidos</h3>
  
<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/orders      <b>Method: POST</b>
<br />
<b>Descrição:</b> JSON para criar um pedido com item personalizados e não personalizados conforme o atributo isCustom. Precisa seguir a estrutura abaixo. 
<br /> <b>Permissão: </b> O administrador e o usuário comum podem cadastrar pedidos. 

```
  {
    "creationDate": 1584698576,
    "person": {
      "id": 1
    },
    "items":[
      {
      "isCustom": false,
        "quantity": 4,
        "snack": { 
          "id": 3
        }
      }
      {
        "isCustom": true,
        "quantity": 1,
        "snack": { 
          "items": [
            {
              "ingredient": {
                "id": 3,
                "name": "Hambúrguer",
                "price": 3							
              },
              "piece": 3
            },
            { 
              "ingredient": {
                "id": 4,
                "name": "Ovo",
                "price": 0.8							
              },
              "piece": 2
            }
          ]
        }
      }
    ]
  }
```

<h3>Cancelar pedido</h3>
<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/orders/<b>{id}</b>/cancel      <b>Method: PUT</b>
<br />
<b>Descrição: </b> Muda o status do pedido para cancelado (CANCELED). 
<br /> <b>Permissão: </b> O administrador pode cancelar qualquer pedido em aberto (PREPARING) e o usuário comum apenas pode cancelar os seus pedidos. 

<h3>Finalizar pedidos</h3>
<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/orders/<b>{id}</b>/ready      <b>Method: PUT</b>
<br />
<b>Descrição: </b> Muda o status do pedido para pronto (READY). 
<br /> <b>Permissão: </b> Somente o administrador pode finalizar os pedidos.

<hr />


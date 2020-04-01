# DOCUMENTAÇÃO

<h2>Consultas</h2>

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
<h3>Salvar ingredientes</h3>
  
<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/ingredients      <b>Method: POST</b>
<br />
<b>Descrição:</b> Salvar novo ingrediente
<br /> <b>Permissão: </b> Somente o administrador pode cadastrar ingredientes.

```
  [
    {
      "name":"teste",
      "price": 3
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


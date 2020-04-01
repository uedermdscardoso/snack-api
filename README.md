# DOCUMENTAÇÃO

<h2>Para Consultas</h2>

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/orders  <b>method: GET</b>
<br /> <b>Descrição:</b> Lista de todos os pedidos e somente o administrador tem acesso. 

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/orders/<b>{id}</b>  <b>method: GET</b>
<br /> <b>Descrição:</b> O administrador pode acessar qualquer pedido por meio de seu código e o usuário comum só pode acessar os seus pedidos individualmente. 

<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/orders/my  <b>method: GET</b>
<br /> <b>Descrição:</b> Lista os pedidos do usuário autenticado. O administrador e o usuário comum têm acessos. 

<h3>Salvar pedidos<h3/>
  
<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/orders  <b>method: POST</b>

Descrição: JSON para criar um pedido com item personalizados e não personalizados conforme o atributo isCustom. Precisa seguir essa estrutura. 

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

<h3>Salvar ingredientes<h3/>
  
<b>URL: </b> http://<b>{server}</b>:<b>{port}</b>/ingredients   <b>method: POST</b>
Descrição: Salvar novo ingrediente

```
  [
    {
      "name":"teste",
      "price": 3
    }
  ]
```

Descrição: Atualizar o registro do ingrediente do código 6 (Passando o id)

```
  [
    {
      "id": 6,
      "name":"teste",
      "price": 3
    }
  ]
```


<html>
<body>
<table>
    <tr>
        <th>Product name</th>  <th>Price</th>
    </tr>
    <#list products as product>
    <tr>
        <td>${product.name}</td> <td>$${product.price}</td>
    </tr>
    </#list>
  </table>
</body>
</html>
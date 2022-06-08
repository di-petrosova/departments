<%--
  Created by IntelliJ IDEA.
  User: diana
  Date: 08.06.22
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>
            Department Id
        </th>
        <th>
            Name
        </th>
        <th>
            City
        </th>
        <th>
            Building
        </th>
        <th>
            Street
        </th>
        <th>
            Index
        </th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>1</td>
        <td>Aim</td>
        <td>Kharkiv</td>
        <td>100</td>
        <td>Fesenkovska</td>
        <td>61082</td>
        <td>
            <form action="" method="post">
                <button type="submit">Remove</button>
            </form>
        </td>
        <td>
            <form action="" method="get">
                <button type="submit">Edit</button>
            </form>
        </td>
    </tr>
    <tr>
        <td>2</td>
        <td>Ciclum</td>
        <td>Kiev</td>
        <td>200</td>
        <td>Lemima</td>
        <td>61000</td>
        <td>
            <form action="" method="post">
                <button type="submit">Remove</button>
            </form>
        </td>
        <td>
            <form action="" method="get">
                <button type="submit">Edit</button>
            </form>
        </td>
    </tr>
    <tr>
        <td>3</td>
        <td>Lux</td>
        <td>Lviv</td>
        <td>300</td>
        <td>Ivanova</td>
        <td>55550</td>
        <td>
            <form action="" method="post">
                <button type="submit">Remove</button>
            </form>
        </td>
        <td>
            <form action="" method="get">
                <button type="submit">Edit</button>
            </form>
        </td>
    </tr>
    <tr>
        <td>
            <form action="" method="get">
                <button type="submit">Create department</button>
            </form>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>

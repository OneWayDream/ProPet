<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Спасибо за регистрацию, ${login}!</title>
    <style>
        h1 {
            color: cadetblue;
        }
        h2 {
            color: red;
        }
    </style>
</head>
<body>
    <h1>Ссылка на регистрацию:</h1>
    <h2><a href="${activation_url}/${activation_link}">Пройдите по ссылке</a></h2>
</body>
</html>

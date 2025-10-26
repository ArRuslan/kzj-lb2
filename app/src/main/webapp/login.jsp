<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>
<head>
    <style>
        .d-flex {
            display: flex;
        }

        .row {
            flex-direction: row;
        }

        .col {
            flex-direction: column;
        }

        .gap-2 {
            gap: 1.5rem;
        }
    </style>
</head>
<body>
<h2>Login</h2>
<form method="POST" action="login" class="d-flex col gap-2">
    <input name="login" placeholder="Login" />
    <input name="password" placeholder="Password" type="password" />

    <button type="submit">Login</button>
</form>

</body>
</html>

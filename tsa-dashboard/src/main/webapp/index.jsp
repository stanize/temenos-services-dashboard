<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Temenos Services Dashboard</title>
    <style>
        body { font-family: Arial; background: #f4f6f8; margin: 0; padding: 0; }
        .header { background: #003366; color: white; padding: 15px; text-align: center; }
        .container { display: flex; justify-content: center; margin-top: 40px; gap: 40px; }
        .card { background: white; border-radius: 10px; padding: 20px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); width: 250px; text-align: center; }
        .status { font-size: 18px; margin: 10px 0; }
        .running { color: green; font-weight: bold; }
        .stopped { color: red; font-weight: bold; }
        .button { padding: 10px 20px; border: none; border-radius: 5px; background: #007BFF; color: white; cursor: pointer; }
        .button:hover { background: #0056b3; }
    </style>
</head>
<body>
    <div class="header">
        <h2>Temenos Services Dashboard</h2>
    </div>

    <div class="container">
        <div class="card">
            <h3>JBoss Status</h3>
            <div class="status running"><%= request.getAttribute("jbossStatus") %></div>
        </div>

        <div class="card">
            <h3>TSM Service</h3>
            <div class="status running"><%= request.getAttribute("tsmStatus") %></div>
                <form action="dashboard" method="post">
                    <input type="hidden" name="action" value="restart" />
                    <button class="button" type="submit">Restart TSM</button>
                </form>
        </div>
    </div>
</body>
</html>

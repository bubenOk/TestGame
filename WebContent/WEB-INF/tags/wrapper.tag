<%@tag description="Base Page template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<html>
  <head>
  	<title>TestGame</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
  </head>
  <body style="margin: 0 auto; min-width: 240px; max-width: 320px;background-color:black">
    <div id="body" style="width:320px; background-color:gray; min-height:200px; text-align:center">
      <jsp:doBody/>
    </div>
    <div id="pagefooter" class="tC gray">
     	DEBUG_INFO
    </div>
  </body>
</html>
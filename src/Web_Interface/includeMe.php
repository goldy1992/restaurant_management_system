<?php

    function includeBootStrap()
    {
        $returnThis = "        <!-- Latest compiled and minified CSS -->
        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css\">

        <!-- Optional theme -->
        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css\">

        <!-- Latest compiled and minified JavaScript -->
        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js\"></script>";
        
        return $returnThis;
    }
    function displayNavBar($name)
    {
        $returnThis = "
                
            <nav class=\"navbar navbar-default\">
                <div class=\"container-fluid\">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class=\"navbar-header\">
                      <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#bs-example-navbar-collapse-1\">
                        <span class=\"sr-only\">Toggle navigation</span>
                        <span class=\"icon-bar\"></span>
                        <span class=\"icon-bar\"></span>
                        <span class=\"icon-bar\"></span>
                      </button>
                      <a class=\"navbar-brand\" href=\"index.php\">Restaurant Web Interface</a>
                    </div>

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\">
                      <ul class=\"nav navbar-nav\">";

        if (strpos($name, 'index.php') !== FALSE) 
        {
            $returnThis .= "<li class=\"active\"><a href=\"index.php\">Home<span class=\"sr-only\">(current)</span></a></li>";
        } // if
        else 
        {
            $returnThis .= "<li ><a href=\"index.php\">Home</a></li>";
        } // else


        if (strpos($name, 'addItem.php') !== FALSE) 
        {
            $returnThis .= "<li class=\"active\"><a href=\"addItem.php\">Add Item<span class=\"sr-only\">(current)</span></a></li>";
        } // if
        else 
        {
            $returnThis .= "<li ><a href=\"addItem.php\">Add Item</a></li>";
        } // else   
        
        if (strpos($name, 'removeItem.php') !== FALSE) 
        {
            $returnThis .= "<li class=\"active\"><a href=\"removeItem.php\">Remove Item<span class=\"sr-only\">(current)</span></a></li>";
        } // if
        else 
        {
            $returnThis .= "<li ><a href=\"removeItem.php\">Remove Item</a></li>";
        } // else


        $returnThis .= "
                      </ul>


                    </div><!-- /.navbar-collapse -->
                </div><!-- /.container-fluid -->
            </nav>";
        
        return $returnThis;
    }

?>
<?php
    include 'includeMe.php';
    

function removePage($page, $con)
{
    $idQuery = "DELETE FROM 3YP_POS_IN_MENU WHERE ID = '" . $page . "'";  
    $result = mysqli_query($con, $idQuery); 
    
    $idQuery = "DELETE FROM 3YP_MENU_PAGES WHERE NAME = '" . $page . "'";  
    $result = mysqli_query($con, $idQuery); 
        
}



function selectPages($con)
{
    
    $result = mysqli_query($con, "SELECT NAME FROM 3YP_MENU_PAGES") or die("Error " . mysqli_error($con));

    $array = array();

    if ($result->num_rows > 0) 
    {
        // output data of each row
        while($row = $result->fetch_assoc()) 
        {
            $array[] = $row["NAME"];
        }
    } // if
    else
    {
        printf("no fucking results!");
    }
    mysqli_free_result($result);
 
    return $array;
}

$con = mysqli_connect("dbhost.cs.man.ac.uk","mbbx9mg3","Fincherz+2013") or die("Error " . mysqli_error($link));
mysqli_select_db($con, "mbbx9mg3");


if(isset($_POST["submit_button"]))
{

    $itemsList = $_POST['pages'];
    $message = "";
    


    foreach($_POST['pages'] as $check) 
    {
           
        removePage($check, $con);
    }
    
    $message = "Removed Successfully!";
} // isInserted
        
    


$array = selectPages($con);

mysqli_close($con);


?>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Remove Item</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <?php echo includeBootStrap(); ?>
    </head>
    <body <?php    if (isset($_POST["submit_button"])) {
        echo "onload=\"alert('" . $message . "')\"";
    }
    ?> >
        
        <?php echo displayNavBar(__FILE__); ?>
<SCRIPT> javaSays(); </SCRIPT>
        <h1>Remove Item From Database</h1>
        <form method="post">
        <table>
            <tr>
                <td><h3>Page Name</h3></td>
            </tr>

                    <?php
                    for ($i = 0; $i < sizeof($array); $i++) 
                    {
                        
                        print "<tr>   
                                    <td>
                 <input type=\"checkbox\" name=\"pages[]\" value=\"" . $array[$i] . "\" "; 
               
                        
                        print ">  ". $array[$i] ."                </td>
            </tr>";
                    }
                    ?>

            
      
            
            <tr>
                <td colspan="2"><input type="submit" value="Submit" name="submit_button"></td>
            </tr>
            
            
        </table>
        </form>
    </body>
</html>

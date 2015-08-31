<?php
    include 'includeMe.php';
    
function insertPage($page_name, $parentPage, $buttonName, $con)
{
   
    $insert_page_query = "INSERT INTO 3YP_MENU_PAGES VALUES ('" . 
                            $page_name. "', '" . $parentPage . "', '" . $buttonName 
                            . "')";
    
    //echo "query: " . $insert_item_query;
    
    $reason = "success";
    $success = true;
    
    if (!mysqli_query($con, $insert_page_query))
    {
        $reason = "Error insert 3YP_ITEMS VALUES: " . mysqli_error($con);
        echo $reason . "\n";
        $success = false;
    }
    
    return array($success, $reason);
} 

function getItemID($item_name, $con)
{
    $idQuery = "SELECT ID FROM 3YP_ITEMS WHERE NAME = '" . $item_name . "'"; 
    $result = mysqli_query($con, $idQuery); 
    $newItemID;
    
    if ($result->num_rows > 0) 
    {
        // output data of each row
        $row = $result->fetch_assoc(); 
        $newItemID = $row["ID"];
        //echo "ID = " . $newItemID;
    } // if
    
    else
        printf("no fucking results!");
        
    return $newItemID;
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
        printf("no results!");
    }
    mysqli_free_result($result);
 
    return $array;
}


$con = mysqli_connect("dbhost.cs.man.ac.uk","mbbx9mg3","Fincherz+2013") or die("Error " . mysqli_error($link));
mysqli_select_db($con, "mbbx9mg3");


$validName = false;
$validButtonName = false;



if(isset($_POST["submit_button"]))
{
    $page_name = $_POST["page_name"];
    $pPage = $_POST['parent_page'];
    $bName = $_POST['button_name'];
 
    $message = "";
    
    
    $validName = !empty($page_name);
    $validButtonName = !empty($bName);

    

    if ($validName && $validButtonName)
    {
        $insertedItem = insertPage($page_name, $pPage, $bName, $con);
        
        $message .= "Successfully Added To Database!";
    } // if valid input
    else
        $message .= "NOT VALID\n name " . $validName . "\n price " . $validPrice .
             "\n quantity " . $validQuantity . "\n";
    
} // if post


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
        <title>Insert Item</title>
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
        <h1>Add Item To Database</h1>
        <form method="post">
        <table>
            <tr>
                <td colspan="2">Item Name</td>
                <td><input type="text" name="page_name" value="<?php if(isset($_POST["submit_button"])) echo $page_name; ?>"></td>
            </tr>

            <tr>
                <td colspan="2" align="top">Parent Page</td>
                <td>
                    <?php
                    for ($i = 0; $i < sizeof($array); $i++) 
                    {
                        print "<input type=\"radio\" name=\"parent_page\" value=\"" . $array[$i] . "\" "; 
                        
                        if ($i == 0) echo "checked";
                        
                        print ">  ". $array[$i] ."<br>\n";
                    }
                    ?>
                </td>
            </tr>
            <tr>
                <td colspan="2">Button Name</td>
                <td><input type="text" name="button_name" value="<?php if(isset($_POST["submit_button"])) echo $bName; ?>"></td>
            </tr>
            
            
            <tr>
                <td colspan="2"><input type="submit" value="Submit" name="submit_button"></td>
            </tr>
            
            
        </table>
        </form>
    </body>
</html>

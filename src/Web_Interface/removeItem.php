<?php
    include 'includeMe.php';
    
function insertItem($item_name, $price, 
                    $quantity, $stock_count, 
                    $age_check, $food_or_drink, $con)
{
    //print "called insert_item \n";
    $insert_item_query = "INSERT INTO 3YP_ITEMS VALUES (NULL, '" . 
                            $item_name . "', " . $price . ", " . $quantity 
                            . ", " . $stock_count . ", " . $age_check . ", '"
                            . $food_or_drink . "')";
    
    //echo "query: " . $insert_item_query;
    
    $reason = "success";
    $success = true;
    
    if (!mysqli_query($con, $insert_item_query))
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
        printf("no fucking results!");
    }
    mysqli_free_result($result);
 
    return $array;
}

function selectItems($con)
{
    
    $result = mysqli_query($con, "SELECT * FROM 3YP_ITEMS") or die("Error " . mysqli_error($con));

    $array = array();

    if ($result->num_rows > 0) 
    {
        // output data of each row
        while($row = $result->fetch_assoc()) 
        {
            $array[] = $row;
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
$validPrice = false;
$validQuantity = false;
$insertedItem = array(false, "");
$insertedPages = array(false, "");



if(isset($_POST["submit_button"]))
{
    $item_name = $_POST["item_name"];
    $price = $_POST["price"];
    $quantity = $_POST["quantity"];
    $stock_count = $_POST["stock_count"];
    $age_check = $_POST["age_check"];
    $pagesList = $_POST['pages'];
    $f_o_d = $_POST['food_drink'];  
    $message = "";
    
    $food_or_drink;
    ($f_o_d == 0) ? $food_or_drink = "FOOD" : $food_or_drink = "DRINK";
    
   /* echo "item: " . $item_name . "\n" .
          "price: " . $price . "\n" .
          "quantity: " . $quantity . "\n" .
          "stock_count: " . $stock_count . "\n" .
          "age_check: " . $age_check . "\n" .
          "food or drink: " . $food_or_drink . "\n";*/
    
    $validName = !empty($item_name);
    $validPrice = is_numeric($price) && ($price > 0);
    $validQuantity = is_numeric($quantity) && ($quantity >= 0);
    

    if ($validName && $validPrice && $validQuantity)
    {
        $insertedItem = insertItem($item_name, $price, 
                    $quantity, $stock_count, 
                    $age_check, $food_or_drink, $con);

        if ($insertedItem[0])
        {
            $newItemID = getItemID($item_name, $con);
            foreach($_POST['pages'] as $check) 
            {
                insertItemPagePosition($newItemID, $check, $con);
            }
        } // isInserted
        
        $message .= "Successfully Added To Database!";
    } // if valid input
    else
        $message .= "NOT VALID\n name " . $validName . "\n price " . $validPrice .
             "\n quantity " . $validQuantity . "\n";
    
} // if post


$array = selectItems($con);

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
        <h1>Remove Item To Database</h1>
        <form method="post">
        <table>
            <tr>
                <td>Item Name</td>
            </tr>
            <tr>   
                <td>
                    <?php
                    for ($i = 0; $i < sizeof($array); $i++) 
                    {
                        print "<input type=\"checkbox\" name=\"pages[]\" value=\"" . $array[$i] . "\" "; 
                        
                        if(isset($_POST["submit_button"]))
                        {
                            $match = false;
                            for ($t = 0; $t < count($pagesList); $t++)
                                if (strcmp($pagesList[$t], $array[$i]["NAME"]) == 0)
                                        $match = true;
                            
                            if ($match == true)
                                echo "checked";
                           
                        } // if
                        
                        print ">". $array[$i] ."<br>\n";
                    }
                    ?>
                </td>
            </tr>
            
      
            
            <tr>
                <td colspan="2"><input type="submit" value="Submit" name="submit_button"></td>
            </tr>
            
            
        </table>
        </form>
    </body>
</html>

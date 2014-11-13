<?php

$con = mysqli_connect("dbhost.cs.man.ac.uk","mbbx9mg3","Fincherz+2013") or die("Error " . mysqli_error($link));
mysqli_select_db($con, "mbbx9mg3");

if(isset($_POST["submit_button"]))
{
    $item_name = $_POST["item_name"];
    $price = $_POST["price"];
    $quantity = $_POST["quantity"];
    $stock_count = $_POST["stock_count"];
    $age_check = $_POST["age_check"];
    
    echo "item: " . $item_name . "\n" .
          "price: " . $price . "\n" .
          "quantity: " . $quantity . "\n" .
          "stock_count: " . $stock_count . "\n" .
          "age_check: " . $age_check . "\n" ;
    


    $insert_item_query = "INSERT INTO 3YP_ITEMS VALUES (NULL, '" . $item_name . "', " .
    $price . ", " . $quantity . ", " . $stock_count . ", " . $age_check . ")";
    
    echo "query: " . $insert_item_query;
    
    mysqli_query($con, $insert_item_query) or die("Error " . mysqli_error($con));
    
    $idQuery = "SELECT ID FROM 3YP_ITEMS WHERE NAME = '" . $item_name . "'";
    
    $result = mysqli_query($con, $idQuery);
    
    $newItemID;
    
    if ($result->num_rows > 0) 
    {
        // output data of each row
        $row = $result->fetch_assoc(); 
        $newItemID = $row["ID"];
        echo "ID = " . $newItemID;
    } // if
    
    else
    {
        printf("no fucking results!");
    }
    
    foreach($_POST['pages'] as $check) 
     if ($check == null)
         echo "not checked\n";
     else
         echo "checked " . $check . "\n";
    
    
} // if post


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
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">


    </head>
    <body>
<SCRIPT> javaSays(); </SCRIPT>
        <h1>Add Item To Database</h1>
        <form method="post">
        <table>
            <tr>
                <td>Item Name</td>
                <td><input type="text" name="item_name"></td>
            </tr>
            <tr>
                <td>Price</td>
                <td><input type="text" name="price"></td>
            </tr>        
            <tr>
                <td>Quantity</td>
                <td><input type="text" name="quantity"></td>
            </tr>
            <tr>
                <td>Stock Count</td>
                <td><input type="radio" name="stock_count" value=0 checked> On<br>
                    <input type="radio" name="stock_count" value=1> Off</td>
            </tr>
            <tr>
                <td>Need Age Check</td>
                <td><input type="radio" name="age_check" value=0 > Yes<br>
                    <input type="radio" name="age_check" value=1 checked> No</td>
            </tr>
            <tr>
                <td>Add to Pages</td>
                <td>
                    <?php
                    for ($i = 1; $i < sizeof($array); $i++) 
                    {
                        print "<input type=\"checkbox\" name=\"pages[]\" value=\"" . $array[$i] . "\" \>". $array[$i] ."<br>";
                        
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

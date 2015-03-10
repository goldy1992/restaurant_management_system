<?php
    include 'includeMe.php';
    
    class item
    {
        var $name;
        var $id;
        var $quantity;
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
    } // if
    
    else
        printf("no results!");
        
    return $newItemID;
}  

function updateStock($itemID, $amount, $con)
{
    $idQuery = "UPDATE `3YP_ITEMS` SET QUANTITY = '" . $amount . "' WHERE ID = '" . $itemID . "'";  
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

function selectItems($con)
{
    
    $result = mysqli_query($con, "SELECT * FROM 3YP_ITEMS") or die("Error " . mysqli_error($con));

    $array = array();

    if ($result->num_rows > 0) 
    {
        // output data of each row
        while($row = $result->fetch_assoc()) 
        {
            $item = new item;
            $item -> name = $row["NAME"];
            $item -> id = $row["ID"];
            $item -> quantity = $row["QUANTITY"];
            $array[] = $item;
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

  $validNewAmount = false;

if(isset($_POST["submit_button"]))
{

    $itemSelected = $_POST['item'];
    $newAmount = $_POST['newAmount'];
    $message = "";
    
    $validNewAmount = is_numeric($newAmount) && $newAmount >= 0;
    

    if ($validNewAmount)
    {
        $id = getItemID($itemSelected, $con);
        updateStock($id, $newAmount, $con);
        
        $message = "Stock updated successfully!";
        
    }
    else
    {
        $message = "Please check your input again!\n  is int: " . is_int($newAmount) . ", >= 0 : " . ($newAmount >= 0) . "";
    }
} // isInserted
        
    


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
        <title>Update Stock</title>
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
        <h1>Item List</h1>
        <form method="post">
        <table>
            <tr>
                <td><h3>Item Name</h3></td>
                <td><h3>Current Stock Count</h3></td>
            </tr>

                    <?php
                    for ($i = 0; $i < sizeof($array); $i++) 
                    {
                        print " <tr>   
                <td>
                        <input type=\"radio\" name=\"item\" value=\"" . $array[$i] -> name . "\" "; 
               
                         if ($i == 0) echo "checked";                       
                  
                        print ">  ". $array[$i] -> name ."</td>
                            <td> " . $array[$i] -> quantity . " </td>
            </tr>";
                    }
                    ?>    
            
            <tr> 
                <td>New Count</td>
                <td><input type="text" name="newAmount" value="" ></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Submit" name="submit_button"></td>
            </tr>
            
            
        </table>
        </form>
    </body>
</html>

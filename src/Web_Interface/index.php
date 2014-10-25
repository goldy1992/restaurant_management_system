<?php

$con = mysqli_connect("dbhost.cs.man.ac.uk","mbbx9mg3","Fincherz+2013") or die("Error " . mysqli_error($link)); 

mysqli_select_db($con, "mbbx9mg3");

$result = mysqli_query($con, "SELECT NAME FROM 3YP_MENU_PAGES");

$array = array();

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        $array[] = $row["NAME"];
    }
}
else
    printf("no fucking results!");
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
        <form>
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
                <td><input type="radio" name="stock_count" value="On" checked> On<br>
                    <input type="radio" name="stock_count" value="Off"> Off</td>
            </tr>
            <tr>
                <td>Need Age Check</td>
                <td><input type="radio" name="stock_count" value="On" > Yes<br>
                    <input type="radio" name="stock_count" value="Off" checked> No</td>
            </tr>
                        <tr>
                            <td colspan="2"><input type="submit" value="Submit"></td>
            </tr>
                <td>Add to Pages</td>
                <td>
                    <?php
                    print sizeof($array);
                    for ($i = 1; $i < sizeof($array); $i++) {
                    print "<input type=\"checkbox\" name=\"pages\" value=\"" . $array[$i] . "\\>". $array[$i] ."<br>";}
                    ?>
                </td>
            </tr>
            
            
        </table>
        </form>
    </body>
</html>

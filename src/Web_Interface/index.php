<?php include 'includeMe.php'; ?>
<html>
    <head>
<?php echo includeBootStrap(); ?>
    </head>
    <body onload="alert('<?php echo __FILE__; ?>')">
<?php echo displayNavBar(__FILE__); ?>
    </body>
</html>
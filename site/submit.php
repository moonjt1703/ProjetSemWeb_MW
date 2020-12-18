<?php 

header(Location :"http://localhost:8888");

require_once( "sparqllib.php" );
 
$db = sparql_connect( "http://localhost:3030/transportEnCommun-Stetienne/sparql" );
if( !$db ) { print sparql_errno() . ": " . sparql_error(). "\n"; exit; }
sparql_ns( "PublicTransportLine","http://www.example.com/PublicTransportLigne" );

$sparql = "PREFIX rdf: http://xmlns.com/foaf/0.1/ 
SELECT DISTINCT * WHERE { ?x a rdf:PublicTransportLine .} ";
 
$sparql1 = "SELECT DISTINCT * WHERE { ?x a rdf:PublicTransportLigne .} ";
$result = sparql_query( $sparql ); 
if( !$result ) { print sparql_errno() . ": " . sparql_error(). "\n"; exit; }
 
$fields = sparql_field_array( $result );
 
print "<p>Number of rows: ".sparql_num_rows( $result )." results.</p>";
print "<table class='example_table'>";
print "<tr>";
foreach( $fields as $field )
{
	print "<th>$field</th>";
}
print "</tr>";
while( $row = sparql_fetch_array( $result ) )
{
	print "<tr>";
	foreach( $fields as $field )
	{
		print "<td>$row[$field]</td>";
	}
	print "</tr>";
}
print "</table>";
 
 


 ?>
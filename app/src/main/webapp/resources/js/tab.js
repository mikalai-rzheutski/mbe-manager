var d = document;

function addLayer(rowIndex) {
    var structureTable = d.getElementById('editTab').getElementsByTagName('TBODY')[0];
    var newRow = structureTable.insertRow(rowIndex + 1);
    newRow.innerHTML = d.getElementById('editTab').rows[rowIndex].innerHTML;
    newRow.attributes = d.getElementById('editTab').rows[rowIndex].attributes;
}

function deleteLayer(rowIndex) {
    var structureTable = d.getElementById('editTab').getElementsByTagName('TBODY')[0];
    if (structureTable.rows.length > 3) structureTable.deleteRow(rowIndex);
    else {
        for (colIndex = 0; colIndex < structureTable.rows[rowIndex].cells.length; colIndex++) {
            if (structureTable.rows[2].cells[colIndex].getElementsByTagName('input').length == 1) {
                structureTable.rows[2].cells[colIndex].getElementsByTagName('input')[0].value = ""
            }
            if (structureTable.rows[2].cells[colIndex].getElementsByTagName('select').length > 0) {
                structureTable.rows[2].cells[colIndex].getElementsByTagName('select')[0].value = "1"
            }
        }
    }
}

function rowNumbering(id) {
    var table = d.getElementById(id).getElementsByTagName('TBODY')[0];
    for (rowIndex = 2; rowIndex < table.rows.length; rowIndex++) {
        table.rows[rowIndex].getElementsByTagName('input')[0].value = rowIndex - 1;
    }
}

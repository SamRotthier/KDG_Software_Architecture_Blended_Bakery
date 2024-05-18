window.addEventListener("load", init)

let ingredients = []

async function init(){
    ingredients = await fetchIngredients()
    document.getElementById("add-ingredient-btn").addEventListener("click", addIngredient)
    console.log("init complete")
}

async function fetchIngredients() {
    const response = await fetch('/api/ingredients');
    return await response.json();
}

function deleteRow(button) {
    let row = button.closest("tr");
    row.remove();
}

function addIngredient(){
    console.log("start event")
    //ingredient
    let ingredientTable = document.getElementById("ingredient-table")

    let tr = document.createElement("tr");
    let ingredientData = document.createElement("td")
    ingredientData.setAttribute("class", "p-3")
    let ingredientSelect = document.createElement("select")
    let ingredientLabel = document.createElement("label")

    ingredientSelect.classList.add("form-select", "ingredient-select")
    let defaultOption = document.createElement("option")
    defaultOption.innerText = "Select an ingredient"
    defaultOption.setAttribute("class", "text-muted")
    defaultOption.setAttribute("value", "")
    defaultOption.value = ""
    ingredientSelect.appendChild(defaultOption)

    ingredients.forEach(ingredient => {
        let option = document.createElement("option")
        option.value = ingredient.id
        option.innerText = ingredient.name
        ingredientSelect.appendChild(option)
    })

    ingredientData.appendChild(ingredientSelect)

    //quantity
    let quantityData = document.createElement("td")
    quantityData.setAttribute("class", "p-3")
    let quantityInput = document.createElement("input")
    quantityInput.type = "number"
    quantityInput.placeholder = "Quantity"
    quantityInput.classList.add("form-control")
    quantityData.appendChild(quantityInput)

    //delete button
    let deleteData = document.createElement("td");
    deleteData.classList.add("text-center", "p-3")
    let deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.classList.add("btn", "btn-danger");
    deleteButton.innerText = "Delete";
    deleteButton.onclick = function () {
        ingredientTable.removeChild(tr);
    };
    deleteData.appendChild(deleteButton);

    tr.appendChild(ingredientData)
    tr.appendChild(quantityData)
    tr.appendChild(deleteData)
    ingredientTable.appendChild(tr)
    console.log("end event")
}
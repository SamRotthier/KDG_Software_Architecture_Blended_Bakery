window.addEventListener("load", init)

let ingredients = []

async function init(){
    ingredients = await fetchIngredients()
    document.getElementById("add-ingredient-btn").addEventListener("click", addIngredient)
    document.getElementById("add-step-btn").addEventListener("click", addRecipeStep)
    console.log("init complete")
}

async function fetchIngredients() {
    const response = await fetch('/api/ingredients');
    return await response.json();
}

function deleteRow(button) {
    console.log("start delete")
    button.parentElement.parentElement.remove()
}

function addIngredient(){
    console.log("start event")
    //ingredient
    let ingredientTable = document.getElementById("ingredient-table")

    let tr = document.createElement("tr");
    let ingredientData = document.createElement("td")
    ingredientData.setAttribute("class", "p-3")
    let ingredientSelect = document.createElement("select")

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

    tr.appendChild(ingredientData)
    tr.appendChild(quantityData)
    tr.appendChild(deleteButton())
    ingredientTable.appendChild(tr)
    console.log("end event")
}

function addRecipeStep(){
    let recipeTable = document.getElementById("recipe-step-table")
    let count = recipeTable.rows.length;

    let tr = document.createElement("tr")
    let stepData = document.createElement("td")
    let stepInput = document.createElement("input")
    stepInput.type = "hidden"
    stepInput.value = (count+1).toString()
    stepInput.name = "steps["+count+"].step"
    stepData.appendChild(stepInput)

    let stepSpan = document.createElement("span")
    stepSpan.innerText = (count + 1).toString()
    stepData.appendChild(stepSpan)
    stepData.setAttribute("name", "steps[" + count + "].step")
    stepData.classList.add("text-center", "p-3")


    let descrData = document.createElement("td")
    let descrInput = document.createElement("input")
    let descrDiv = document.createElement("div")
    descrData.classList.add("form-floating", "p-3")
    descrInput.classList.add("form-control")
    descrInput.type="text"
    descrInput.placeholder="Step"
    descrInput.id="steps["+count+ "].text"
    descrInput.name = "steps["+count+ "].text"
    descrDiv.appendChild(descrInput)
    descrData.appendChild(descrDiv)

    tr.appendChild(stepData)
    tr.appendChild(descrData)
    tr.appendChild(deleteButton())
    recipeTable.appendChild(tr)
}

function deleteButton(){
    let deleteData = document.createElement("td");
    deleteData.classList.add("text-center", "p-3")
    let deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.classList.add("btn", "btn-danger");
    deleteButton.innerText = "Delete";
    deleteButton.setAttribute("onclick", "deleteRow(this)")
    deleteData.appendChild(deleteButton);

    return deleteData
}
const createForm = document.getElementById("createForm");
const recipeOutput = document.getElementById("readDiv");

createForm.addEventListener('submit', function (event) {
    event.preventDefault();
    console.log(this.name);
    const data = {
        name: this.name.value,
        type: this.type.value,
        flavour: this.flavour.value
    }

    fetch("http://localhost:8081/create/Recipe", { //Make request
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            'Content-Type': "application/json"
        }
    }).then(response => { // Receive response
        return response.json(); // Convert response body to json
    }).then(data => { //json data from previous .then()
        renderRecipes();
        this.reset();
    }).catch(error => console.log(error));
});

function renderRecipes() {
    fetch("http://localhost:8081/get/allRecipe")
        .then(response => response.json())
        .then(arrayOfRecipes => {
            console.log("Recipes: ", arrayOfRecipes);
            recipeOutput.innerHTML = '';
            arrayOfRecipes.forEach(function(recipe) {  
        
                const card = document.createElement("div");
                card.className = "card";
                // card.setAttribute("class", "card");
                recipeOutput.appendChild(card);

                const cardBody = document.createElement("div");
                cardBody.className = "card-body";
                card.appendChild(cardBody);

                const title = document.createElement("h5");
                title.className = "card-title";
                title.innerText = recipe.name;
                cardBody.appendChild(title);

                const type = document.createElement("p");
                type.className = "card-body";
                type.innerText = "Type: " + recipe.type;
                cardBody.appendChild(type);

                const flavour = document.createElement("p");
                flavour.className = "card-body";
                flavour.innerText = "Flavour: " + recipe.flavour;
                cardBody.appendChild(flavour);

                const deleteButton = document.createElement("a");
                deleteButton.className = "card-link";
                deleteButton.innerText = "Delete";
                deleteButton.addEventListener("click", function () {
                    deleteRecipe(recipe.id);
                })
                cardBody.appendChild(deleteButton);

                const editButton = document.createElement("a");
                editButton.className = "card-link";
                editButton.innerText = "Edit";
                editButton.addEventListener("click", function () {
                    editRecipe(recipe.id, 1);
                    cardBody.appendChild(editButton)
                    cardBody.appendChild(title);
                })
                cardBody.appendChild(editButton);
            });
        
        }).catch(error => console.error(error));
}
renderRecipes();

function deleteRecipe(id) {
    fetch("http://localhost:8081/delete/recipe/" + id, {
        method: "DELETE"
    }).then(response => renderRecipes())
    .catch(error => console.error(error));
}

function editRecipe(id, update){
    fetch("http://localhost:8081/patch/combatAC/" + id,{
        method: "PATCH",
        body: JSON.stringify(update),
        headers: {
            'Content-Type': "application/json",
        }
    }).then(response => renderRecipes())
    .catch(error => console.log(error));
}
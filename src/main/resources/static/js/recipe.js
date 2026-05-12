const API = "8a5e7cf2d3b548f69c65064d49254b39"
const API2 = "BIdj0ISg3q4x2cMKVyQgl5lKWlnqvg6VUASZlOEQ"
const recipe = document.querySelector("#recipe");
const recipeBtn = document.querySelector("#recipeBtn");
const food = document.querySelector("#food");
const foodBtn = document.querySelector("#foodBtn");
const holder = document.querySelector(".holder")

// Function to get recipes
const getRecipes = async () => {
  // Validates if input field was filled out
  if(recipe.value === "") {
    alert("Please fill out recipe field")
    return;
  }

  holder.innerHTML = "";

  const data = await fetch(`https://api.spoonacular.com/recipes/complexSearch?query=${recipe.value}&number=20&addRecipeNutrition=true&apiKey=${API}`);
  const recipeData = await data.json();
  console.log(recipeData);

  for (let info of recipeData.results) {
    const div = document.createElement("div");
    div.setAttribute("class", "recipe-row");

    div.innerHTML = `
      <div class="ff-result-layout">
        <img src="${info.image}" alt="${info.title}">
        <div>
          <h3>${info.title}</h3>
          ${info.summary}
          <p>Prep time: <b>${info.readyInMinutes} minutes</b></p>
          <p>Servings: <b>${info.servings}</b></p>
          <p>Calories per serving: <b>${info.nutrition.nutrients[0].amount}</b></p>
          <p>Carbs: <b>${info.nutrition.nutrients[3].amount}g</b> | Fat: <b>${info.nutrition.nutrients[1].amount}g</b> | Protein: <b>${info.nutrition.nutrients[8].amount}g</b></p>
          <p><a href="${info.sourceUrl}" target="_blank" rel="noopener">Open recipe</a></p>
        </div>
      </div>
    `;

    holder.append(div);
  }
}

// Function to get food information
const getFood = async () => {
  // Validates if input field was filled out
  if(food.value === "") {
    alert("Please fill out food field");
    return;
  }

  holder.innerHTML = "";

  const data = await fetch(`https://api.nal.usda.gov/fdc/v1/foods/search?pageSize=10&query=${food.value}&api_key=${API2}`);
  const recipeData = await data.json();
  console.log(recipeData);

  for (let info of recipeData.foods) {
    const div = document.createElement("div");
    div.setAttribute("class", "food-result");

    // Use `description` for food name instead of `lowercaseDescription`
    const foodName = info.description || "No name available"; 

    // Handle nutrient data safely
    const calories = info.foodNutrients.find(nutrient => nutrient.nutrientName === "Energy")?.value || "N/A";
    const protein = info.foodNutrients.find(nutrient => nutrient.nutrientName === "Protein")?.value || "N/A";
    const fat = info.foodNutrients.find(nutrient => nutrient.nutrientName === "Total Fat")?.value || "N/A";
    const carbs = info.foodNutrients.find(nutrient => nutrient.nutrientName === "Carbohydrate, by difference")?.value || "N/A";

    div.innerHTML = `
      <div class="ff-result-layout">
        <div></div>
        <div>
          <h3>${foodName}</h3>
          <p><b>${calories}</b> calories per serving</p>
          <p>${protein}g protein | ${fat}g fat | ${carbs}g carbs</p>
        </div>
      </div>
    `;
    holder.append(div);
  }
}

// Event Listeners
recipeBtn.addEventListener("click", getRecipes);
foodBtn.addEventListener("click", getFood);

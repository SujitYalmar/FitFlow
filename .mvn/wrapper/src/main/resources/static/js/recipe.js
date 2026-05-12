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

    const divideLine = document.createElement("div");
    divideLine.setAttribute("class", "divider");
    const section = document.createElement("section");
    section.setAttribute("class", "section");

    section.innerHTML = `
      <h5 class="center-align teal-text food-title">${info.title}</h5>
      <div class="col s12 m8 offset-m2 l6 offset-l3">
        <div class="card-panel blue-grey lighten-5 z-depth-1 food-card">
          <div class="row valign-wrapper">
            <div class="col s3">
              <img src="${info.image}" alt="" class="circle responsive-img">
            </div>
            <div class="col s10">
              <span class="black-text">
                ${info.summary}
              </span>
              <p>Prep Time: <b>${info.readyInMinutes} minutes</b></p>
              <p>Make this many servings: <b>${info.servings} servings</b></p>
              <p>Total Calories(kcal) Per Serving: <b>${info.nutrition.nutrients[0].amount}</b></p>
              <p>Carbohydrates(g) Per Serving: <b>${info.nutrition.nutrients[3].amount}</b></p>
              <p>Fat(g) Per Serving: <b>${info.nutrition.nutrients[1].amount}</b></p>
              <p>Protein(g) Per Serving: <b>${info.nutrition.nutrients[8].amount}</b></p>
              <p>Recipe Link: <a href="${info.sourceUrl}">${info.sourceUrl}</a></p>
            </div>
          </div>
        </div>
      </div>
    `;

    div.append(divideLine, section);
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
    div.setAttribute("class", "row");

    // Use `description` for food name instead of `lowercaseDescription`
    const foodName = info.description || "No name available"; 

    // Handle nutrient data safely
    const calories = info.foodNutrients.find(nutrient => nutrient.nutrientName === "Energy")?.value || "N/A";
    const protein = info.foodNutrients.find(nutrient => nutrient.nutrientName === "Protein")?.value || "N/A";
    const fat = info.foodNutrients.find(nutrient => nutrient.nutrientName === "Total Fat")?.value || "N/A";
    const carbs = info.foodNutrients.find(nutrient => nutrient.nutrientName === "Carbohydrate, by difference")?.value || "N/A";

    div.innerHTML = `
      <div class="col s6 offset-s3">
        <div class="card-panel blue-grey lighten-5">
          <h3 class="food-title">${foodName}</h3>
          <h6>${calories} Calories per serving</h6>
          <p>${protein} g of Protein per serving</p>
          <p>${fat} g of Fat per serving</p>
          <p>${carbs} g of Carbohydrates per serving</p>
        </div>
      </div>
    `;
    holder.append(div);
  }
}

// Event Listeners
recipeBtn.addEventListener("click", getRecipes);
foodBtn.addEventListener("click", getFood);

console.log("Script loaded");

// Get the current theme from localStorage or default to "light"
let currentTheme = getTheme();

// Set theme in localStorage
function setTheme(theme) {
  localStorage.setItem("theme", theme);
}
  
  // Get the stored theme from localStorage, or default to "light"
function getTheme() {
  return localStorage.getItem("theme") || "light";
}

/* When DOM is fully loaded, apply the initial theme
document.addEventListener("DOMContentLoaded", () => {
  // Apply the theme to the page
  changePageTheme(currentTheme, "");
  console.log(`Initial theme applied: ${currentTheme}`);
  
  // Add the event listener for theme change
  const changeThemeButton = document.querySelector("#toggle_mode_button");
  changeThemeButton.addEventListener("click", () => {
    let oldTheme = currentTheme;

    // Toggle the theme
    currentTheme = currentTheme === "dark" ? "light" : "dark";
    console.log(`Theme changed to: ${currentTheme}`);

    // Apply the new theme
    changePageTheme(currentTheme, oldTheme);
  });
});

// Apply the current theme to the page and update the theme toggle button
function changePageTheme(theme, oldTheme) {
  // Store the new theme in localStorage
  setTheme(theme);

  // Remove the old theme class, if applicable
  if (oldTheme) {
    document.querySelector("html").classList.remove(oldTheme);
  }

  // Add the new theme class to the <html> element
  document.querySelector("html").classList.add(theme);

  // Update the text of the theme change button
  document.querySelector("#toggle_mode_button span").textContent =
    theme === "light" ? "Dark" : "Light";
}
don't know why this method not worked on smaller screen after some time that's why
we used event delegation in the lower method */



document.addEventListener("DOMContentLoaded", () => {
  // Apply the theme to the page
  changePageTheme(currentTheme, "");
  console.log(`Initial theme applied: ${currentTheme}`);

  // Event delegation: Listen for clicks anywhere in the document
  document.addEventListener("click", (event) => {
    // Check if the clicked element is the toggle button (works for both main and sidebar toggle buttons)
    const changeThemeButton = event.target.closest("#toggle_mode_button");

    if (changeThemeButton) {
      console.log("Dark mode toggle button clicked!");
      let oldTheme = currentTheme;
      currentTheme = currentTheme === "dark" ? "light" : "dark";
      changePageTheme(currentTheme, oldTheme);
    }
  });
});

// Apply the current theme to the page and update the theme toggle button
function changePageTheme(theme, oldTheme) {
  // Store the new theme in localStorage
  setTheme(theme);

  // Remove the old theme class, if applicable
  if (oldTheme) {
    document.querySelector("html").classList.remove(oldTheme);
  }

  // Add the new theme class to the <html> element
  document.querySelector("html").classList.add(theme);

  // Update the text of the theme change button
  document.querySelectorAll("#toggle_mode_button span").forEach((span) => {
    span.textContent = theme === "light" ? "Dark" : "Light";
  });
}

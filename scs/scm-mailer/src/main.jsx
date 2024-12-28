// import React from 'react'
// import { createRoot } from 'react-dom/client'
// import App from './App'
// // import './styles/email-module.css'

// const mount = () => {
//   const rootElement = document.getElementById('react-root')
//   if (!rootElement) {
//     console.error('Failed to find the react-root element')
//     return
//   }

//   try {
//     const root = createRoot(rootElement)
//     root.render(
//       <React.StrictMode>
//         <App />
//       </React.StrictMode>
//     )
//     console.log('React component mounted successfully')
//   } catch (error) {
//     console.error('Error mounting React component:', error)
//   }
// }

// // Try mounting after a short delay to ensure DOM is ready
// setTimeout(mount, 100)

// // Also try mounting on DOMContentLoaded
// document.addEventListener('DOMContentLoaded', mount)

import React from 'react'
import { createRoot } from 'react-dom/client'
import App from './App'

// Remove the multiple mounting attempts and just use a single mount
const rootElement = document.getElementById('react-root')
if (rootElement) {
  const root = createRoot(rootElement)
  root.render(
    <React.StrictMode>
      <App />
    </React.StrictMode>
  )
}
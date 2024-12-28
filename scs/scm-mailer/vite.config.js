// import { defineConfig } from 'vite'
// import react from '@vitejs/plugin-react'

// export default defineConfig({
//   plugins: [react()],
//     server: {
//       port: 5173,
//         proxy: {
//         '/api': {
//         target: 'http://localhost:8080',
//         changeOrigin: true,
//         secure: false,
//       }
//     }
//   }
// });

// import { defineConfig } from 'vite'
// import react from '@vitejs/plugin-react'

// export default defineConfig({
//   plugins: [
//     react({
//       babel: {
//         presets: ['@babel/preset-react']
//       }
//     })
//   ],
//   server: {
//     port: 5173,
//     proxy: {
//       '/api': {
//         target: 'http://localhost:8080',
//         changeOrigin: true,
//         secure: false,
//       }
//     }
//   }
// })
// export default defineConfig({
//   server: {
//     port: 5173,
//     strictPort: true,
//     proxy: {
//       '/api': {
//         target: 'http://localhost:8080',
//         changeOrigin: true,
//         rewrite: (path) => path.replace(/^\/api/, '')
//       },
//       '/user': {
//         target: 'http://localhost:8080',
//         changeOrigin: true
//       }
//     }
//   },
//   resolve: {
//     alias: {
//       '@': '/src'
//     }
//   }
// })

// import { defineConfig } from 'vite'
// import react from '@vitejs/plugin-react'
// import path from 'path'

// export default defineConfig({
//   plugins: [react()],
//   server: {
//     port: 5173,
//     strictPort: true,
//     proxy: {
//       '/api': {
//         target: 'http://localhost:8080',
//         changeOrigin: true,
//         rewrite: (path) => path.replace(/^\/api/, '')
//       },
//       '/user': {
//         target: 'http://localhost:8080',
//         changeOrigin: true
//       }
//     }
//   },
//   resolve: {
//     alias: {
//       '@': path.resolve(__dirname, './src')
//     }
//   },
// })


// import { defineConfig } from 'vite'
// import react from '@vitejs/plugin-react'
// import path from 'path'

// export default defineConfig({
//   plugins: [react()],
//   server: {
//     cors: {
//       // origin: '*',
//       origin: 'http://localhost:8080', // Allow Spring Boot server
//       credentials: true,
//       methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
//       allowedHeaders: ['Content-Type', 'Authorization']
//     },
//     port: 5173,
//     strictPort: true,
//     proxy: {
//       '/api': {
//         target: 'http://localhost:8080',
//         changeOrigin: true,
//         rewrite: (path) => path.replace(/^\/api/, '')
//       },
//       '/user': {
//         target: 'http://localhost:8080',
//         changeOrigin: true
//       }
//     },
//   },
//   resolve: {
//     alias: {
//       '@': path.resolve(__dirname, './src')
//     }
//   },
//   base: '/',
//   build: {
//     outDir: 'dist',
//     assetsDir: 'assets',
//     manifest: true,
//     rollupOptions: {
//       input: 'src/main.jsx'
//     }
//   }
// })


// import { defineConfig } from 'vite'
// import reactSwc from '@vitejs/plugin-react-swc'
// import path from 'path'

// export default defineConfig({
//   plugins: [
//     reactSwc({
//       fastRefresh: true
//     })
//   ],
//   server: {
//     port: 5173,
//     strictPort: true,
//     cors: {
//       origin: 'http://localhost:8080',
//       credentials: true,
//       methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
//       allowedHeaders: ['Content-Type', 'Authorization']
//     },
//     proxy: {
//       '/api': {
//         target: 'http://localhost:8080',
//         changeOrigin: true,
//         secure: false
//       }
//     },
//     hmr: {
//       protocol: 'ws',
//       host: 'localhost',
//       port: 5173
//     }
//   },
//   resolve: {
//     alias: {
//       '@': path.resolve(__dirname, './src')
//     }
//   },
//   base: '/',
//   optimizeDeps: {
//     include: ['react', 'react-dom']
//   }
// })


import { defineConfig } from 'vite'
import reactSwc from '@vitejs/plugin-react-swc'
import path from 'path'

export default defineConfig({
  plugins: [
    reactSwc({
      fastRefresh: true
    })
  ],
  server: {
    port: 5173,
    strictPort: true,
    cors: {
      origin: ['http://localhost:8080', 'http://localhost:5173'],
      credentials: true
    },
    hmr: {
      protocol: 'ws',
      host: 'localhost',
      port: 5173,
      clientPort: 5173
    }
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  }
})

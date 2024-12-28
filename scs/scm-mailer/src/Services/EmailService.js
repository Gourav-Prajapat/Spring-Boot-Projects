import { customAxios } from "./Helper";

export async function sendEmail(emailData){
    
    const result = (await customAxios.post('/email/send-text', emailData)).data
    return result;
}

// export async function sendEmailWithFile(formData) {
//     const result = (await customAxios.post("/email/send-with-attachments", formData)).data;
//     return result;
// }

// export async function sendEmailWithFile(formData) {
//     try {
//         for (let [key, value] of formData.entries()) {
//             console.log(`${key}:`, value);
//         }

//         const result = (await customAxios.post("/email/send-with-attachments", formData, {
//             headers: {
//                 "Content-Type": "multipart/form-data", // Ensure this header is set
//             },
//         })).data;
//         return result;
//     } catch (error) {
//         console.error("Error sending email with file:", error);
//         throw error;
//     }
// }

export async function sendEmailWithFile(formData) {
    try {
        // Create request object to match backend EmailRequest structure
        const request = {
            to: formData.get('to'),
            subject: formData.get('subject'),
            body: formData.get('body')
        };

        // Create a new FormData for the actual request
        const requestFormData = new FormData();
        
        // Append the email request data as JSON blob
        // This matches the @RequestPart("request") in the controller
        requestFormData.append('request', 
            new Blob([JSON.stringify(request)], 
            {type: 'application/json'})
        );

        // Get all files and append them
        // This matches the @RequestParam("files") List<MultipartFile> in the controller
        const files = formData.getAll('files');
        files.forEach(file => {
            requestFormData.append('files', file);
        });

        const result = await customAxios.post("/email/send-with-attachments", 
            requestFormData,
            {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            }
        );

        return result.data;
    } catch (error) {
        console.error("Error sending email with file:", error);
        console.error("Response data:", error.response?.data);
        throw error;
    }
}

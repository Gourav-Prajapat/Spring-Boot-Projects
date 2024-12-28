import React, { useState, useRef } from 'react'
import toast, { Toaster } from 'react-hot-toast';
import { sendEmail, sendEmailWithFile } from '../Services/EmailService';
import { Editor } from '@tinymce/tinymce-react';

function EmailSender() {
    const [emailData, setEmailData] = useState({
        to: "",
        subject: "",
        body: "",
    });

    const [sending, setSending] = useState(false);
    const editorRef = useRef(null);
    const [attachment, setAttachment] = useState(null); // hold selected files

    function handleFieldChange(event, name) {
        setEmailData({ ...emailData, [name]: event.target.value });
    }

    function handleFileChange(event) {
        const files = Array.from(event.target.files); // Convert FileList to Array
        if (files.length > 0) {
            setAttachment(files); // Store all selected files
            toast.success(`${files.length} file(s) selected`);
        } else {    
            setAttachment(null);
        }
    }

    async function handleSubmit(event) {
        event.preventDefault();
        if (emailData.to === '' || emailData.subject === '' || emailData.body === '') {
            toast.error("Please fill all required fields!");
            return;
        }
    
        const formData = new FormData();
        formData.append("to", emailData.to);
        formData.append("subject", emailData.subject);
        formData.append("body", emailData.body);
    
    // Check if there are attachments
    if (attachment && attachment.length > 0) {
        try {
            setSending(true);
            
            // Append each file to FormData
            attachment.forEach((file) => {
                formData.append("files", file);
            });
            
            // Send email with attachments
            await sendEmailWithFile(formData);
            toast.success("Email sent successfully with attachments!");
            
            // Reset form
            resetForm();
        } catch (error) {
            console.error("Error details:", error);
            toast.error("Failed to send email with attachments");
        } finally {
            setSending(false);
        }
    } else {
        // Send email without attachments
        try {
            setSending(true);
            await sendEmail(emailData);
            toast.success("Email sent successfully!");
            
            // Reset form
            resetForm();
        } catch (error) {
            console.error("Error details:", error);
            toast.error("Failed to send email");
        } finally {
            setSending(false);
        }
    }
}

// Helper function to reset form
function resetForm() {
    setEmailData({
        to: "",
        subject: "",
        body: "",
    });
    setAttachment(null);
    if (editorRef.current) {
        editorRef.current.setContent("");
    }

    setTimeout(() => {
        const toInput = document.getElementById('to_input');
        const subjectInput = document.getElementById('subject_input');
        if (toInput) toInput.value = '';
        if (subjectInput) subjectInput.value = '';
    }, 0);
}
    return (
        // <div className="w-full min-h-screen flex justify-center items-center">
            <div className="email_card bg-white md:w-2/3 w-full mx-4 mb-10 md:mx-0 p-5 border-t-8 rounded-xl  shadow dark:bg-gray-800 dark:hover:bg-gray-700 dark:text-white border-blue-700 ">
                <h1 className="text-gray-900 text-3xl mb-5 dark:text-white">Send Email</h1>
                <form action="" onSubmit={handleSubmit}>
                    <div className="input_field">
                        <label htmlFor="to_input" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">To</label>
                        <input type="text" id="to_input" value={emailData.to} onChange={(event) => handleFieldChange(event, "to")} className="mb-3 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="example@gmail.com" />

                        <label htmlFor="subject_input" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Subject</label>
                        <input type="text" id="subject_input" value={emailData.subject} onChange={(event) => handleFieldChange(event, "subject")} className="mb-3 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Enter here" />

                        <div className="message_form">
                            <label htmlFor="body_input" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Message</label>
        
                            <Editor id="file-picker"
                                onEditorChange={(event) => {
                                    setEmailData({ ...emailData, 'body': editorRef.current.getContent() })
                                }}
                                onInit={(evt, editor) => editorRef.current = editor}
                                initialValue="<p>Write Your Message.</p>"
                                apiKey='7ua0mbw7mhw90asw3oivt9sydbljm4vww0qids1s4zphqhoc'
                                init={{
                                    height: 500,
                                    selector: 'Editor#file-picker',
                                    plugins: [
                                        // Core editing features
                                        'image', 'code', 'anchor', 'autolink', 'charmap', 'codesample', 'emoticons', 'image', 'link', 'lists', 'media', 'searchreplace', 'table', 'visualblocks', 'wordcount',
                                        
                                        'checklist', 'mediaembed', 'casechange', 'export', 'formatpainter', 'pageembed', 'a11ychecker', 'tinymcespellchecker', 'permanentpen', 'powerpaste', 'advtable', 'advcode', 'editimage', 'advtemplate', 'ai', 'mentions', 'tinycomments', 'tableofcontents', 'footnotes', 'mergetags', 'autocorrect', 'typography', 'inlinecss', 'markdown',
                                        // Early access to document converters
                                        'importword', 'exportword', 'exportpdf'
                                    ],
                                    toolbar: 'undo redo | blocks fontfamily fontsize | bold italic underline strikethrough | link image media table mergetags | addcomment showcomments | spellcheckdialog a11ycheck typography | align lineheight | checklist numlist bullist indent outdent | emoticons charmap | removeformat | link image | code',
                                    tinycomments_mode: 'embedded',
                                    tinycomments_author: 'Author name',
                                    mergetags_list: [
                                        { value: 'First.Name', title: 'First Name' },
                                        { value: 'Email', title: 'Email' },
                                    ],
                                    ai_request: (request, respondWith) => respondWith.string(() => Promise.reject('See docs to implement AI Assistant')),
                                    exportpdf_converter_options: { 'format': 'Letter', 'margin_top': '1in', 'margin_right': '1in', 'margin_bottom': '1in', 'margin_left': '1in' },
                                    exportword_converter_options: { 'document': { 'size': 'Letter' } },
                                    importword_converter_options: { 'formatting': { 'styles': 'inline', 'resets': 'inline', 'defaults': 'inline', } },
                                }}
                            />
                        </div>
                        
                        <label htmlFor="file_input" className="block mt-4 mb-2 text-sm font-medium text-gray-900 dark:text-white">Attachments</label>
                        <input
                            type="file"
                            id="file_input"
                            multiple // Enable selecting multiple files
                            onChange={handleFileChange}
                            className="mb-3 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                        />

                        {/* loader */}
                        {sending && (<div className="loader flex flex-col items-center justify-center gap-2 mt-4">
                            <div role="status">
                                <svg aria-hidden="true" class="w-8 h-8 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor" />
                                    <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill" />
                                </svg>
                                <span class="sr-only">Loading...</span>
                            </div>
                            <h1>Sending Email...</h1>
                        </div>)}
                        <div className="button_container flex justify-center">
                            <button disabled={sending} type="submit" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mt-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Send</button>
                        </div>
                    </div>
                </form>
            </div>
        // </div>
    )
}
export default EmailSender

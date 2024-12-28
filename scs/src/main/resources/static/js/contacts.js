console.log("contacts.js is running");

const baseURL = "http://localhost:8080";

// deleteContact
async function deleteContact(id){
    
    Swal.fire({
        title: "Do you want to Delete the Contact?",
        showCancelButton: true,
        confirmButtonText: "Delete",
        buttonsStyling: true,
        customClass: {
            confirmButton: 'bg-red-500 text-white px-4 py-2 rounded',
            cancelButton: 'bg-gray-500 text-white px-4 py-2 rounded'
        }
      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            const url = `${baseURL}/user/contacts/delete/${id}`;
            window.location.replace(url);
            Swal.fire({
                title: "Deleted!",
                text: "",
                icon: "success",
                confirmButtonText: "OK", // Explicitly set the button text
                buttonsStyling: false, // Prevent default styling
                customClass: {
                    confirmButton: 'bg-green-500 text-white px-4 py-2 rounded', 
                }
            });
        }
      });
}

// view contact

const viewContactModal = document.getElementById('view_contact_modal')
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'view_contact_modal',
    override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
    contactModal.show();
}

function closeContactModal() {
    contactModal.hide();
}

async function loadContactData(id) {
    console.log(id);
    try {
        const data = await (await fetch(`${baseURL}/api/contacts/${id}`)).json();
        console.log(data);

        // Set name, email, and image
        document.querySelector("#contact_name").innerHTML = data.name || "No name provided";
        document.querySelector("#contact_email").innerHTML = data.email || "No email provided";
        document.querySelector("#contact_image").src = data.contactImage || "default-image-url.png";
        document.querySelector("#contact_phoneNumber").innerHTML = data.phoneNumber || "No phone number provided";
        
        // Address and description with defaults
        document.querySelector("#contact_address").innerHTML = data.address || "No address provided";
        document.querySelector("#contact_description").innerHTML = data.description || "No description available";

        // Favorite check
        const contactFavorite = document.querySelector("#contact_favorite");
        if (data.favorite) {
            contactFavorite.innerHTML = "<i class='fa-solid fa-heart' style='color: #f70808;'></i><i class='fa-solid fa-heart' style='color: #f70808;'></i><i class='fa-solid fa-heart' style='color: #f70808;'></i>";
        } else {
            contactFavorite.innerHTML = "Not a favorite Contact";
        }

        // Website and LinkedIn links with default text
        const contactWebsite = document.querySelector("#contact_website");
        if (data.websiteLink) {
            contactWebsite.href = data.websiteLink;
            contactWebsite.innerHTML = data.websiteLink;
        } else {
            contactWebsite.innerHTML = "No website link";
            contactWebsite.removeAttribute("href");
        }

        const contactLinkedIn = document.querySelector("#contact_linkedIn");
        if (data.linkedInLink) {
            contactLinkedIn.href = data.linkedInLink;
            contactLinkedIn.innerHTML = data.linkedInLink;
        } else {
            contactLinkedIn.innerHTML = "No LinkedIn link";
            contactLinkedIn.removeAttribute("href");
        }

        openContactModal();
    } catch (error) {
        console.log("Error:", error);
    }
}

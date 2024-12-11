import httpClient from "../http-common";

const create = (formData) => {
    return httpClient.post("/solicitudCredito/", formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
    });
};

const evaluateCredit = id => {
    return httpClient.put(`/evaluacionCredito/evaluateCredit/${id}`);
}

const updateStatus = id => {
    return httpClient.put(`/evaluacionCredito/updateStatus/${id}`);
}

const updateTerms = (formData) => {
    return httpClient.put("/evaluacionCredito/updateTerms", formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
    });
}

const rejectTerms = id => {
    return httpClient.put(`/solicitudCredito/rejectTerms/${id}`);
}

const getcredits = () => {
    return httpClient.get(`/solicitudCredito/all`);
}

const getCredits = id => {
    return httpClient.get(`/seguimiento/credits/${id}`);
}

export default { create, evaluateCredit, updateStatus, updateTerms, rejectTerms, getcredits, getCredits };
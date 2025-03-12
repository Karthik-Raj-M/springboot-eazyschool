package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.bean.ContactBean;
import com.eazybytes.eazyschool.config.EazySchoolProps;
import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContactService {

    @Autowired
    public ContactRepository contactRepository;

    @Autowired
    EazySchoolProps eazySchoolProps;


    public boolean SaveAllMessage(ContactBean contactBean) {
        boolean isSaved = false;

        contactBean.setStatus(EazySchoolConstants.OPEN);
        ContactBean savedContact = contactRepository.save(contactBean);
        if (null != savedContact && savedContact.getContactId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }
    public Page<ContactBean> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir){

        int pageSize = eazySchoolProps.getPageSize();
        if (eazySchoolProps.getContact()!=null && eazySchoolProps.getContact().get("pageSize")!=null){
            pageSize = Integer.parseInt(eazySchoolProps.getContact().get("pageSize").trim());
        }
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<ContactBean> msgPage = contactRepository.findByStatusWithQuery(
                EazySchoolConstants.OPEN,pageable);
        return msgPage;
    }


    public boolean updateMsgStatus(int contactId) {
        boolean isUpdated = false;
        int rows = contactRepository.updateStatusById(EazySchoolConstants.CLOSE,contactId);
        if (rows>0) {
            isUpdated = true;
        }
        return isUpdated;
    }
}

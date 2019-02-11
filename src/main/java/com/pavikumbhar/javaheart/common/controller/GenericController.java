
package com.pavikumbhar.javaheart.common.controller;

import com.pavikumbhar.javaheart.common.service.GenericService;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author pavikumbhar
 */
@Slf4j
public abstract class GenericController<E, PK extends Serializable> {

    @Autowired
    private GenericService<E, PK> genericService; //Service which will do all data retrieval/manipulation work

   
    /**
     * Retrieve All
     *
     * @return
     */
    @RequestMapping(value = "/findAll/", method = RequestMethod.GET)
    public ResponseEntity<List<E>> findAll() {

        try {
            List<E> findAll = genericService.findAll();
            if (findAll.isEmpty()) {
                return new ResponseEntity<List<E>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<List<E>>(findAll, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Retrieve findAllActive
     *
     * @return
     */
    @RequestMapping(value = "/findAllActive/", method = RequestMethod.GET)
    public ResponseEntity<List<E>> findAllActive() {

        try {
            List<E> getAll = genericService.findAll();
            if (getAll.isEmpty()) {
                return new ResponseEntity<List<E>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<List<E>>(getAll, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve Single Record
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<E> findById(@PathVariable("id") PK id) {
        try {
            log.info("Fetching  with id  {} ", id);
            E entity = genericService.findById(id);
            if (entity == null) {
                log.info("Record with id   {}  ", id + " not found");
                return new ResponseEntity<E>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<E>(entity, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Add
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save/", method = RequestMethod.POST)
    public ResponseEntity<Void> save(@RequestBody E entity) {

        try {
            genericService.add(entity);
            HttpHeaders headers = new HttpHeaders();

            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param id
     * @param entity
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<E> update(@PathVariable("id") PK id, @RequestBody E entity) {

        try {
            E current = genericService.findById(id);

            if (current == null) {
                log.info("record with id   {}   not found",id);
                return new ResponseEntity<E>(HttpStatus.NOT_FOUND);
            }

            genericService.update(entity);
            return new ResponseEntity<E>(current, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete Record
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<E> remove(@PathVariable("id") PK id) {

        try {
            log.info("Fetching & Deleting Record with id : {}" , id);

            E entity = genericService.findById(id);
            if (entity == null) {
                log.info("Unable to delete. Record with id  {}  not found",id);
                return new ResponseEntity<E>(HttpStatus.NOT_FOUND);
            }

            genericService.remove(entity);
            return new ResponseEntity<E>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

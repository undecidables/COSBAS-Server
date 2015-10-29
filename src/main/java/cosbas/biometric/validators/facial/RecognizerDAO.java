package cosbas.biometric.validators.facial;

import org.bytedeco.javacpp.opencv_core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@author Renette}
 */
@Component
public class RecognizerDAO {

    public final static String TRAINING_FACES = "trainingFaces";
    public final static String EIGEN_VECTORS = "eigenVectors";
    public final static String AVERAGE = "average";
    public final static String EIGEN_VALUES = "eigenValues";
    public final static String PROJECTED_FACE = "projectedFace";
    public final static String PERSON_NUMBERS = "personNumbers";
    public final static String PERSON_NAMES = "personNames";
    @Autowired
    private DataComponentDAO componentDAO;

    @CacheEvict(value = "complete-recognizer",beforeInvocation = true, allEntries = true)
    RecognizerData save(RecognizerData entity) {
        LocalDateTime updated = entity.updated;
        NameStore names = componentDAO.save(new NameStore(PERSON_NAMES, updated, entity.personNames, entity.needsTraining()));
        String linkedID = names.getId();

        componentDAO.save(entity.trainingFaces.stream().map(tf -> new CvMatStorage(tf, TRAINING_FACES, updated, linkedID)).collect(Collectors.toList()));
        componentDAO.save(entity.eigenVectors.stream().map(tf -> new CvMatStorage(tf, EIGEN_VECTORS, updated, linkedID)).collect(Collectors.toList()));
        componentDAO.save(new CvMatStorage(entity.pAvgTrainImg, AVERAGE, updated, linkedID));
        componentDAO.save(new CvMatStorage(entity.eigenValues, EIGEN_VALUES, updated, linkedID));
        componentDAO.save(new CvMatStorage(entity.projectedTrainFace, PROJECTED_FACE, updated, linkedID));
        componentDAO.save(new CvMatStorage(entity.personNumTruthMat, PERSON_NUMBERS, updated, linkedID));

        return entity;
    }

    @CacheEvict(value = "complete-recognizer",beforeInvocation = true, allEntries = true)
    void deleteAll(){
        componentDAO.deleteAll();
    }

    @Cacheable("complete-recognizer")
    RecognizerData findFirstByOrderByUpdatedDesc() {
        NameStore nameStore =  componentDAO.findFirstByOrderBySavedDesc();
        List<String> names = nameStore.getNames();
        LocalDateTime updated = nameStore.getSaved();
        boolean needsTraining = nameStore.isNeedsTraining();
        String linkedID = nameStore.getId();

        List<opencv_core.IplImage> trainingFaces = componentDAO.findByFieldAndLinkedID(TRAINING_FACES, linkedID).stream().map(CvMatStorage::getIPL).collect(Collectors.toList());
        List<opencv_core.IplImage> eigenVectors = componentDAO.findByFieldAndLinkedID(EIGEN_VECTORS, linkedID).stream().map(CvMatStorage::getIPL).collect(Collectors.toList());
        opencv_core.IplImage pAvgTrainImg = componentDAO.findFirstByFieldAndLinkedID(AVERAGE, linkedID).getIPL();
        opencv_core.CvMat eigenValues = componentDAO.findFirstByFieldAndLinkedID(EIGEN_VALUES, linkedID).getCVMat();
        opencv_core.CvMat projectedTrainFace = componentDAO.findFirstByFieldAndLinkedID(PROJECTED_FACE,linkedID ).getCVMat();
        opencv_core.CvMat personNumTruthMat = componentDAO.findFirstByFieldAndLinkedID(PERSON_NUMBERS, linkedID).getCVMat();


        return new RecognizerData(names, trainingFaces, eigenVectors, pAvgTrainImg, eigenValues, projectedTrainFace, personNumTruthMat, updated, needsTraining, linkedID);

    }

   void saveTraining(RecognizerData data) {
        NameStore n = (NameStore) componentDAO.findById(data.getNameID());
        n.setNeedsTraining(data.needsTraining());
        componentDAO.save(n);
    }
}

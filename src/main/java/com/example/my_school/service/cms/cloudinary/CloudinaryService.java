package com.example.my_school.service.cms.cloudinary;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.my_school.config.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary){
        this.cloudinary=cloudinary;
    }

    public Map<String, String> upload(MultipartFile file, String folder) {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder
                    )
            );

            Map<String, String> data = new HashMap<>();

            data.put("url", result.get("secure_url").toString());
            data.put("publicId", result.get("public_id").toString());

            return data;

        } catch (IOException e) {
            return null;
        }
    }

    public void deleteImage(String publicId) throws IOException {
        try{
            if(publicId==null || publicId.isEmpty()){
                throw new ApiException("Không thể xác định được publicId của ảnh");
            }
            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap("resource_type", "image")
            );
        }catch (ApiException apiException){
            log.error("[ERROR] deleteImage {} "+apiException.getMessage());
            throw  new ApiException("Delete image failse in cloudinary");
        }

    }
}

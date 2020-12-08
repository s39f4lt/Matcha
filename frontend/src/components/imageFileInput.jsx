import React, { useContext, useState } from "react";
import FileInput from "./common/fileInput";
import { toBase64 } from "../utils/fileToBase64";
import { uploadImage } from "../services/imageService";
import UserContext from "../contexts/userContext";

const ImageFileInput = ({ name, label, userId }) => {
  const [image, setImage] = useState(null);
  const userContext = useContext(UserContext);

  const handleChange = async (e) => {
    const file = e.target.files[0];
    if (file) {
      const filename = file.name.split(".")[0];
      const imageBase64 = await toBase64(file);
      const image = {
        name: filename,
        base64: imageBase64,
        userId: userId,
      };
      setImage(image);
    }
  };

  const handleUploadButtonClick = async () => {
    if (image) {
      await uploadImage(image);
      userContext.handleNewImages();
    }
  };

  return (
    <FileInput
      name={name}
      label={label}
      onChange={handleChange}
      onUploadButtonClick={handleUploadButtonClick}
    />
  );
};

export default ImageFileInput;

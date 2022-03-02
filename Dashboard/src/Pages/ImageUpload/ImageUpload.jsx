import React, { useState } from "react";
import "./ImageUpload.css";
import Avatar from "@mui/material/Avatar";
import PublishIcon from "@mui/icons-material/Publish";
import { storage } from "../../Firebase";
import { confirm } from "react-confirm-box";
import { ref, getDownloadURL, uploadBytesResumable } from "firebase/storage";

export default function ImageUpload() {
  const [progress, setProgress] = useState(0);
  const [image, setImage] = useState(null);
  const [url, setUrl] = useState(null);
  const [imageName, setImageName] = useState("");

  const handleImageChange = (e) => {
    if (e.target.files[0]) {
      setImage(e.target.files[0]);
    }
  };

  const options = {
    labels: {
      confirmable: "Confirm",
      cancellable: "Cancel",
    },
  };
  const uploadFiles = async (file) => {
    const result = await confirm(
      "Are you sure you want to get rid of this data ?",
      options
    );
    if (result) {
      if (!file) return;
      const sotrageRef = ref(storage, `PlasticType/${imageName}`);
      const uploadTask = uploadBytesResumable(sotrageRef, image, file);
      uploadTask.on(
        "state_changed",
        (snapshot) => {
          const prog = Math.round(
            (snapshot.bytesTransferred / snapshot.totalBytes) * 100
          );
          setProgress(prog);
          getDownloadURL(sotrageRef)
            .then((url) => {
              setUrl(url);
            })
            .catch((err) => {
              console.log(err.message, "Error getting the image url");
            });
        },
        (error) => console.log(error),
        () => {
          getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
            console.log("File available at", downloadURL);
            setUrl(downloadURL);
          });
        }
      );
      setImageName(" ");
    }
    return;
  };

  // const handleSubmit = () => {
  //   // const imageRef = ref(storage, `Plastic/${imageName}`);
  //   // uploadBytes(imageRef, image).then(() => {
  //   //   getDownloadURL(imageRef).then((url) => {
  //   //     setUrl(url)
  //   //   }).catch(err => {
  //   //     console.log(err.message, 'Error getting the image url')
  //   //   });
  //   //   setImage(null)
  //   // }).catch(err => {
  //   //   console.log(err.message)
  //   // });
  // }
  return (
    <div className="upload">
      <div className="userUpdateUpload">
        <div className="card">
          <div className="itemCenter">
            <h1>PLASTIC TYPE</h1>
            <input
              type="text"
              placeholder="Plastic name..."
              onChange={(e) => setImageName(e.target.value)}
              value={imageName}
              required
            />
            <Avatar src={url} sx={{ width: 150, height: 150 }} />
            <input type="file" multiple onChange={handleImageChange} />
            <button>
              {" "}
              <label onClick={uploadFiles}>
                {" "}
                <PublishIcon className="userUpdateIcon" alt="" />
              </label>
            </button>
            <input type="file" id="file" style={{ display: "none" }} />
            <h1>Progress {progress}</h1>
          </div>
        </div>
      </div>
    </div>
  );
}

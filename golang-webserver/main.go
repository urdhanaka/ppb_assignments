package main

import (
	"github.com/gin-gonic/gin"
)

const RESULT_DIR = "result"

func post_image(c *gin.Context) {
	_ = c.Request.ParseMultipartForm(2 << 16)
	imageFile := c.Request.MultipartForm.File["picture"][0]

	err := c.SaveUploadedFile(imageFile, RESULT_DIR+"/"+imageFile.Filename)
	if err != nil {
		c.JSON(500, gin.H{
			"error":   err,
			"message": "Server error",
		})
	}

	c.JSON(201, gin.H{
		"err":     nil,
		"message": "image successfully uploaded",
	})
}

func main() {
	r := gin.Default()

	r.POST("/", post_image)

	r.Run(":8080")
}

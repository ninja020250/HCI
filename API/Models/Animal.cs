namespace API.Models
{
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;

    [Table("Animals")]
    public class Animal
    {
        [Key]
        public int Id { get; set; }
        [MaxLength(255)]
        public string Name { get; set; }
        public string AnimationUrl { get; set; }
        [MaxLength(255)]
        public string Type { get; set; }
    }
}